package org.isisaddons.module.command.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.command.app.CommandSpiAppManifest;

public abstract class CommandModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new CommandSpiAppManifest());
    }

}
