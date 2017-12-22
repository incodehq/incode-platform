package org.incode.platform.ext.togglz.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.security.SecurityModule;
import org.incode.example.settings.SettingsModule;
import org.isisaddons.module.togglz.TogglzModule;
import org.incode.domainapp.example.dom.ext.togglz.ExampleDomExtTogglzModule;

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
