package org.isisaddons.module.togglz.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.togglz.TogglzModule;
import domainapp.modules.exampledom.ext.togglz.ExampleDomExtTogglzModule;

public class TogglzExtAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            TogglzModule.class,
            ExampleDomExtTogglzModule.class,
            SecurityModule.class,
            SettingsModule.class
    ).withConfigurationProperty("isis.viewer.wicket.rememberMe.cookieKey","DemoAppEncryptionKey");

    public TogglzExtAppManifest() {
        super(BUILDER);
    }

}
