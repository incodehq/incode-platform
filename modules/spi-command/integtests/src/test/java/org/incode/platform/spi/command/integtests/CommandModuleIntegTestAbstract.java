package org.incode.platform.spi.command.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;
import org.incode.platform.spi.command.integtests.app.CommandSpiAppManifest;

public abstract class CommandModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(CommandSpiAppManifest.BUILDER
                .withAdditionalModules(ExampleDemoSubmodule.class)
        );
    }

}
