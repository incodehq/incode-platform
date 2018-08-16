package org.incode.domainapp.extended.integtests.lib.poly;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class PolyModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new PolyModuleIntegTestModule();
    }

    protected PolyModuleIntegTestAbstract() {
        super(module());
    }

}
