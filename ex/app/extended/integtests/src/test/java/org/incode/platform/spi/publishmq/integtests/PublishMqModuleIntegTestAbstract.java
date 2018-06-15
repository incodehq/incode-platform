package org.incode.platform.spi.publishmq.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.platform.spi.publishmq.integtests.app.PublishMqSpiAppManifest;

public abstract class PublishMqModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(PublishMqSpiAppManifest.BUILDER
                .withAdditionalModules(ExampleDomDemoDomSubmodule.class)
        );
    }


}
