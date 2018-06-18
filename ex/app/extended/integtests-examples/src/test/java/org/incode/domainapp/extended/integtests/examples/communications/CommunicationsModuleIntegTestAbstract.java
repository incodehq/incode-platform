package org.incode.domainapp.extended.integtests.examples.communications;

import javax.inject.Inject;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.app.fakesched.FakeScheduler;

public abstract class CommunicationsModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new CommunicationsModuleIntegTestModule();
    }

    protected CommunicationsModuleIntegTestAbstract() {
        super(module());
    }

    @Inject
    protected FakeScheduler fakeScheduler;

}
