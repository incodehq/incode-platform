package org.incode.domainapp.extended.integtests.examples.settings.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.settings.SettingsModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.settings.FixturesModuleExamplesSettingsSubmodule;

public class SettingsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER =
            Builder.forModules(
                    SettingsModule.class,
                    FixturesModuleExamplesSettingsSubmodule.class,
                    SettingsAppModule.class);

    public SettingsModuleAppManifest() {
        super(BUILDER);
    }

}
