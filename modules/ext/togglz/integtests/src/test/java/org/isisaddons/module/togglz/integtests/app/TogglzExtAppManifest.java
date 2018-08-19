package org.isisaddons.module.togglz.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.togglz.TogglzModule;
import org.isisaddons.module.togglz.fixture.TogglzFixturesModule;

import org.incode.module.settings.SettingsModule;

public class TogglzExtAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            TogglzModule.class,
            TogglzFixturesModule.class,
            //SecurityModule.class,
            SettingsModule.class
    ).withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey","DemoAppEncryptionKey");

    public TogglzExtAppManifest() {
        super(BUILDER);
    }

}
