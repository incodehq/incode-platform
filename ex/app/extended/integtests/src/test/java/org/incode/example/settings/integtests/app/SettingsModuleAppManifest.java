package org.incode.example.settings.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.settings.SettingsModule;

import org.incode.domainapp.module.fixtures.per_cpt.examples.settings.ExampleDomModuleSettingsModule;

public class SettingsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER =
            Builder.forModules(
                    SettingsModule.class,
                    ExampleDomModuleSettingsModule.class,
                    SettingsAppModule.class);

    public SettingsModuleAppManifest() {
        super(BUILDER);
    }

}
