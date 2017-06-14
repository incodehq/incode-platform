package org.incode.module.classification.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.google.inject.util.Providers;

import org.apache.isis.viewer.wicket.viewer.IsisWicketApplication;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;

public class ClassificationModuleApplication extends IsisWicketApplication {

    private static final long serialVersionUID = 1L;

    @Override
    protected void init() {
        super.init();

        getDebugSettings().setOutputMarkupContainerClassName(true);
        getMarkupSettings().setStripWicketTags(true);
        getDebugSettings().setAjaxDebugModeEnabled(false);

        IBootstrapSettings settings = Bootstrap.getSettings();
        settings.setThemeProvider(new BootswatchThemeProvider(BootswatchTheme.Flatly));
    }

    private static final String APP_NAME = "Incode Classification Module Demo";
    
    @Override
    protected Module newIsisWicketModule() {
        final Module isisDefaults = super.newIsisWicketModule();
        
        final Module overrides = new AbstractModule() {
            @Override
            protected void configure() {
                bind(String.class).annotatedWith(Names.named("applicationName")).toInstance(APP_NAME);
                bind(String.class).annotatedWith(Names.named("applicationCss")).toInstance("css/application.css");
                bind(String.class).annotatedWith(Names.named("applicationJs")).toInstance("scripts/application.js");
                bind(String.class).annotatedWith(Names.named("welcomeMessage")).toInstance(readLines(getClass(), "welcome.html"));
                bind(String.class).annotatedWith(Names.named("aboutMessage")).toInstance(APP_NAME);
                bind(InputStream.class).annotatedWith(Names.named("metaInfManifest")).toProvider(Providers.of(getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF")));
            }
        };

        return Modules.override(isisDefaults).with(overrides);
    }

    private static String readLines(final Class<?> contextClass, final String resourceName) {
        try {
            List<String> readLines = Resources.readLines(Resources.getResource(contextClass, resourceName), Charset.defaultCharset());
            return Joiner.on("\n").join(readLines);
        } catch (IOException e) {
            return APP_NAME;
        }
    }

}
