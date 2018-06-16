package org.incode.extended.integtests.spi.command;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.extended.integtests.spi.command.app.CommandSpiAppManifest;

public abstract class CommandModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(CommandSpiAppManifest.BUILDER
                .withAdditionalModules(FixturesModuleSharedSubmodule.class)
        );
    }

}
