package org.incode.extended.integtests.per_cpt.examples.communications;

import javax.inject.Inject;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.extended.integtests.per_cpt.examples.communications.app.CommunicationsModuleAppManifest;
import org.incode.extended.integtests.per_cpt.examples.communications.app.services.fakesched.FakeScheduler;

public abstract class CommunicationsIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(CommunicationsModuleAppManifest.BUILDER
                .withAdditionalModules(ExampleDomDemoDomSubmodule.class)
        );
    }

    @Inject
    protected FakeScheduler fakeScheduler;

}
