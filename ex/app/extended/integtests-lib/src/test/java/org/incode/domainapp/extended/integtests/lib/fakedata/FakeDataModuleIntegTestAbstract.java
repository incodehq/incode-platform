package org.incode.domainapp.extended.integtests.lib.fakedata;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class FakeDataModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new FakeDataModuleIntegTestModule();
    }

    protected FakeDataModuleIntegTestAbstract() {
        super(module());
    }

}
