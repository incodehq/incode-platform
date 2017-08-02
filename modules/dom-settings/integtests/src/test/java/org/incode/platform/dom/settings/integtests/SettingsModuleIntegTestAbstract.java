package org.incode.platform.dom.settings.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.platform.dom.settings.integtests.app.SettingsModuleAppManifest;

public abstract class SettingsModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new SettingsModuleAppManifest());
    }

}
