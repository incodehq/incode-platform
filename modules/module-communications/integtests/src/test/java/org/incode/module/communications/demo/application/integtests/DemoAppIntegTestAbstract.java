package org.incode.module.communications.demo.application.integtests;

import javax.inject.Inject;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.module.communications.demo.application.manifest.CommunicationsModuleAppManifest;
import org.incode.module.communications.demo.application.services.fakesched.FakeScheduler;

public abstract class DemoAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(new CommunicationsModuleAppManifest());
    }

    @Inject
    FakeScheduler fakeScheduler;

}
