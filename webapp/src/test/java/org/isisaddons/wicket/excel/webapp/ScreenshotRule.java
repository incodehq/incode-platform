package org.isisaddons.wicket.excel.webapp;

import geb.Browser;
import geb.report.Base64;
import geb.report.ExceptionToPngConverter;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ScreenshotRule implements MethodRule{

    private static ThreadLocal<Context> contexts = new ThreadLocal<>();
    private static Context getContext() {
        return contexts.get();
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                contexts.set(new Context(base, method, target));
                try {
                    base.evaluate();
                } finally {
                    contexts.set(null);
                }
            }
        };
    }

    /**
     * API: asserts that a screenshot of the current browser matches an existing screenshot, identified by the
     * suffix (and context, ie class name and method).
     *
     * <p>
     *     If existing screenshot is searched for in <code>src/test/resources/com/mycompany/Foo-bar.expected.png</code>
     * </p>
     *
     * <p>
     *     If no existing screenshot can be found then one is automatically created, saved to
     *     <code>src/test/resources/com/mycompany/Foo-bar.actual.png</code>
     * </p>
     */
    public void assertMatches(Browser browser, String suffix) throws IOException {

        final File projectDir = new File(".");
        final File resourcesDir = new File(projectDir, "src/test/resources");

        final byte[] actualScreenshot = Screenshots.screenshot(browser);
        assertThat("unable to take screenshot", actualScreenshot, is(not(nullValue())));

        final File expectedFile = getContext().buildFile(resourcesDir, suffix + ".expected.png");
        final File actualFile = getContext().buildFile(resourcesDir, suffix + ".actual.png");
        final byte[] expectedScreenshot = FileHandling.readBytes(expectedFile);

        if(expectedScreenshot == null) {
            FileHandling.writeBytes(actualScreenshot, actualFile);
            fail("No expected screenshot; written out actual " + actualFile + "\n\n" +
                    openDirCommandLine(actualFile) +
                    viewFileCommandLine(actualFile));
        } else {
            if(!Arrays.equals(expectedScreenshot, actualScreenshot)) {
                FileHandling.writeBytes(actualScreenshot, actualFile);
                fail("Screenshot differs: expected " + expectedScreenshot.length + " bytes vs " + actualScreenshot.length + " bytes" + "\n\n" +
                        openDirCommandLine(actualFile) +
                        diffImagesCommandLine(expectedFile, actualFile));
            }
        }
    }

    private static String diffImagesCommandLine(File expectedFile, File actualFile) throws IOException {
        return quote("C:\\Program Files\\Perforce\\p4merge.exe") + " " + quote(expectedFile.getCanonicalPath()) + " " + quote(actualFile.getCanonicalPath()) + "\n\n\n";
    }

    private static String viewFileCommandLine(File actualFile) throws IOException {
        return quote("C:\\Program Files\\Paint.NET\\PaintDotNet.exe") + " " + quote(actualFile.getCanonicalPath()) + "\n\n\n";
    }

    private static String openDirCommandLine(File file) throws IOException {
        return "explorer.exe" + " " + quote(file.getParentFile().getCanonicalPath()) + "\n\n";
    }

    private static String quote(String str) {
        return "\"" + str + "\"";
    }

    static final class FileHandling {
        private FileHandling(){}

        private static File writeBytes(byte[] bytes, File file) {
            String fileCanonicalPath = file.getName(); // overwritten with canonical path later for exception
            FileOutputStream fileOutputStream = null;
            try {
                mkdirs(file);

                fileCanonicalPath = file.getCanonicalPath();
                fileOutputStream = new FileOutputStream(file);
                ByteStreams.copy(new ByteArrayInputStream(bytes), fileOutputStream);
                return file;
            } catch (IOException e) {
                fail("Failed to write screenshot to " + fileCanonicalPath + "\n\n" + Throwables.getStackTraceAsString(e));
            } finally {
                closeQuietly(fileOutputStream);
            }
            return file;
        }

        private static void mkdirs(File file) throws IOException {
            final File parentFile = file.getParentFile();
            Files.createDirectories(parentFile.toPath());
        }

        private static byte[] readBytes(final File file) {
            try {
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ByteStreams.copy(new FileInputStream(file), baos);
                return baos.toByteArray();
            } catch (IOException e) {
                return null;
            }
        }

        private static void closeQuietly(final Closeable closeable) {
            if(closeable == null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static final class Screenshots {

        private Screenshots(){}

        static byte[] screenshot(Browser browser) {
            // note - this is not covered by tests unless using a driver that can take screenshots
            TakesScreenshot screenshotDriver = determineScreenshotDriver(browser);
            if (screenshotDriver == null) {
                return null;
            }

            try {
                String rawBase64 = screenshotDriver.getScreenshotAs(OutputType.BASE64);
                byte[] decoded = Base64.decode(rawBase64);

                // WebDriver has a bug where sometimes the screenshot has been encoded twice
                if (!isPng(decoded)) {
                    decoded = Base64.decode(decoded);
                }
                return decoded;
            } catch (WebDriverException e) {
                return new ExceptionToPngConverter(e).convert("An exception has been thrown while getting the screenshot:");
            }
        }

        private static TakesScreenshot determineScreenshotDriver(Browser browser) {
            if (browser.getDriver() instanceof TakesScreenshot) {
                return (TakesScreenshot) browser.getDriver();
            }
            if (browser.getAugmentedDriver() instanceof TakesScreenshot) {
                return (TakesScreenshot) browser.getAugmentedDriver();
            }
            return null;
        }

        private static boolean isPng(byte[] bytes) {
            try {
                return new DataInputStream(new ByteArrayInputStream(bytes)).readLong() == 0x89504e470d0a1a0aL;
            } catch (IOException e) {
                // not going to happen
                return false;
            }
        }
    }

    private static class Context {

        private final Statement base;
        private final FrameworkMethod method;
        private final Object target;

        public Context(Statement base, FrameworkMethod method, Object target) {
            this.base = base;
            this.method = method;
            this.target = target;
        }

        File buildFile(final File parentDir, final String suffix) {
            final Class<?> declaringClass = method.getMethod().getDeclaringClass();

            final String packageName = declaringClass.getPackage().getName();
            final String packageDir = packageName.replace('.', '/');
            final String className = declaringClass.getSimpleName();
            final String methodName = method.getName();

            return new File(parentDir, packageDir + "/" + className + "_" + methodName + "-" + suffix);
        }
    }
}
