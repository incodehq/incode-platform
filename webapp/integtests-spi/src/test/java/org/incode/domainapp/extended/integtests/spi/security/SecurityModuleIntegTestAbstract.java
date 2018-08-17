package org.incode.domainapp.extended.integtests.spi.security;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class SecurityModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new SecurityModuleIntegTestModule();
    }

    protected SecurityModuleIntegTestAbstract() {
        super(module());
    }

}
