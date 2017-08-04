package org.incode.platform.dom.communications.integtests;

import javax.inject.Inject;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;
import org.incode.platform.dom.communications.integtests.app.CommunicationsModuleAppManifest;
import org.incode.platform.dom.communications.integtests.app.services.fakesched.FakeScheduler;

public abstract class CommunicationsIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(CommunicationsModuleAppManifest.BUILDER
                .withAdditionalModules(ExampleDemoSubmodule.class)
        );
    }

    @Inject
    protected FakeScheduler fakeScheduler;

}
