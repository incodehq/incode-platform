package org.isisaddons.module.togglz.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.module.settings.SettingsModule;
import org.isisaddons.module.togglz.TogglzModule;

public class TogglzExtAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            TogglzModule.class,
            FixturesModuleExtTogglzSubmodule.class,
            SecurityModule.class,
            SettingsModule.class
    ).withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey","DemoAppEncryptionKey");

    public TogglzExtAppManifest() {
        super(BUILDER);
    }

}
