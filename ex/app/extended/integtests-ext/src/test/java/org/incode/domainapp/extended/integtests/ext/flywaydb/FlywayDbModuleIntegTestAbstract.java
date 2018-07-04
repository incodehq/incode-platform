package org.incode.domainapp.extended.integtests.ext.flywaydb;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class FlywayDbModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new FlywayDbModuleIntegTestModule();
    }

    protected FlywayDbModuleIntegTestAbstract() {
        super(module());
    }


}
