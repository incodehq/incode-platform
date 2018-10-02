package org.isisaddons.module.docx.dom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assume.assumeThat;

public class DocxServiceTest {

    final IoHelper io = new IoHelper(this);

    DocxService docxService;
    WordprocessingMLPackage docxTemplate;

    @org.junit.Before
    public void setUp() throws Exception {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.WARN);

        docxService = new DocxService();

        // given
        final ByteArrayInputStream docxInputTemplate = io.asBais("Template.docx");
        docxTemplate = docxService.loadPackage(docxInputTemplate);

    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public static class Strict extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.STRICT;

        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream docxBaos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, docxBaos, matchingPolicy, DocxService.OutputType.DOCX);

            // then
            final byte[] docxActual = docxBaos.toByteArray();

            // ... for manual inspection
            final File docxExpectedFile = io.asFile("Output-Expected.docx");

            final File docxActualFile = io.asFileInSameDir(docxExpectedFile, "Output-Actual.docx");
            io.write(docxActual, docxActualFile);

            System.out.println("docx expected: " + docxExpectedFile.getAbsolutePath());
            System.out.println("docx actual: " + docxActualFile.getAbsolutePath());


            // ... and automated
            // a simple binary comparison finds differences, even though a manual check using MS Word itself shows
            // no differences; for now just do a heuristic check on file size
            final byte[] docxExpected = io.asBytes(docxExpectedFile);
            assertThat(docxActual.length, isWithin(docxExpected.length, 5));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // then
            expectedException.expectMessage("Input elements [SURPLUS] were not matched to placeholders");

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

        @Test
        public void whenMissingInput() throws Exception {

            // then
            expectedException.expectMessage("Placeholders [Decision2] were not matched to input");

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

    }

    public static class AllowUnmatchedInput extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.ALLOW_UNMATCHED_INPUT;


        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, baos, matchingPolicy);

            // then
            final byte[] actual = baos.toByteArray();
            assertThat(actual.length, greaterThan(0));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions
        }

        @Test
        public void whenMissingInput() throws Exception {

            // then
            expectedException.expectMessage("Placeholders [Decision2] were not matched to input");

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

    }

    public static class AllowUnmatchedPlaceholders extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.ALLOW_UNMATCHED_PLACEHOLDERS;

        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, baos, matchingPolicy);

            // then
            final byte[] actual = baos.toByteArray();
            assertThat(actual.length, greaterThan(0));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // then
            expectedException.expectMessage("Input elements [SURPLUS] were not matched to placeholders");

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);
        }

        @Test
        public void whenMissingInput() throws Exception {

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions

        }

    }

    public static class Lax extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.LAX;

        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, baos, matchingPolicy);

            // then
            final byte[] actual = baos.toByteArray();
            assertThat(actual.length, greaterThan(0));
        }

        @Test
        public void whenSurplusInput() throws Exception {

            // when
            final String html = io.asString("input-surplus.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions
        }

        @Test
        public void whenMissingInput() throws Exception {

            // when
            final String html = io.asString("input-missing.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), matchingPolicy);

            // then no exceptions
        }

    }


    public static class BadInput extends DocxServiceTest {

        @Test
        public void whenBadInput() throws Exception {

            // then
            expectedException.expect(LoadInputException.class);
            expectedException.expectMessage("Unable to parse input");

            // when
            final String html = io.asString("input-malformed.html");
            docxService.merge(html, docxTemplate, new ByteArrayOutputStream(), DocxService.MatchingPolicy.LAX);
        }
    }

    public static class GeneratePdf extends DocxServiceTest {

        private DocxService.MatchingPolicy matchingPolicy = DocxService.MatchingPolicy.STRICT;

        @Before
        public void setUp() throws Exception {
            super.setUp();

            // :-( font mapping issues when running in CI environments
            assumeThat(System.getenv("TRAVIS"), is(nullValue()));
            assumeThat(System.getenv("JENKINS_URL"), is(nullValue()));
            assumeThat(System.getenv("GITLAB_CI"), is(nullValue()));
        }

        @Test
        public void exactMatch() throws Exception {

            // when
            final String html = io.asString("input-exact-match.html");
            final ByteArrayOutputStream pdfBaos = new ByteArrayOutputStream();

            docxService.merge(html, docxTemplate, pdfBaos, matchingPolicy, DocxService.OutputType.PDF);

            // then
            final byte[] pdfActual = pdfBaos.toByteArray();

            // ... for manual inspection
            final File pdfExpectedFile = io.asFile("Output-Expected.pdf");

            final File pdfActualFile = io.asFileInSameDir(pdfExpectedFile, "Output-Actual.pdf");
            io.write(pdfActual, pdfActualFile);

            System.out.println("pdf expected: " + pdfExpectedFile.getAbsolutePath());
            System.out.println("pdf actual: " + pdfActualFile.getAbsolutePath());


            // ... and automated
            // a simple binary comparison finds differences, even though a manual check using MS Word itself shows
            // no differences; for now just do a heuristic check on file size
            final byte[] pdfExpected = io.asBytes(pdfExpectedFile);
            assertThat(pdfActual.length, isWithin(pdfExpected.length, 40));
        }

    }

    private static Matcher<Integer> isWithin(final Integer expected, final int tolerance) {
        return new TypeSafeMatcher<Integer>() {
            @Override
            protected boolean matchesSafely(Integer item) {
                final int length = expected;
                final int lower = length * (100 - tolerance) / 100;
                final int upper = length * (100 + tolerance) / 100;

                return item > lower && item < upper;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" within " + tolerance + "% in size of array with " + expected + " bytes");
            }
        };
    }

}
