package org.incode.domainapp.extended.integtests.spi.publishmq;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.domainapp.extended.integtests.spi.publishmq.app.PublishMqSpiAppManifest;

public abstract class PublishMqModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(PublishMqSpiAppManifest.BUILDER
                .withAdditionalModules(FixturesModuleSharedSubmodule.class)
        );
    }


}
