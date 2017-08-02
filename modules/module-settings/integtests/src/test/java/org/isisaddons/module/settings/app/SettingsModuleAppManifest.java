package org.isisaddons.module.settings.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.settings.SettingsModule;

import domainapp.modules.exampledom.module.settings.ExampleDomModuleSettingsModule;

public class SettingsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER =
            Builder.withModules(
                    SettingsModule.class,
                    ExampleDomModuleSettingsModule.class,
                    SettingsAppModule.class);

    public SettingsModuleAppManifest() {
        super(BUILDER);
    }

}
