package org.isisaddons.module.settings.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.settings.app.SettingsModuleAppManifest;

public abstract class SettingsModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new SettingsModuleAppManifest());
    }

}
