package org.isisaddons.module.togglz.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.togglz.TogglzModule;
import domainapp.modules.exampledom.ext.togglz.ExampleDomExtTogglzModule;

public class TogglzExtAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(
            TogglzModule.class,
            ExampleDomExtTogglzModule.class,
            SecurityModule.class,
            SettingsModule.class
    ).withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey","DemoAppEncryptionKey");

    public TogglzExtAppManifest() {
        super(BUILDER);
    }

}
