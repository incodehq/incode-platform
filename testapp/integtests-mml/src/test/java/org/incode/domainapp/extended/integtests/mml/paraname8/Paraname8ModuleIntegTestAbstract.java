package org.incode.domainapp.extended.integtests.mml.paraname8;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class Paraname8ModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new Paraname8ModuleIntegTestModule();
    }

    protected Paraname8ModuleIntegTestAbstract() {
        super(module());
    }


}
