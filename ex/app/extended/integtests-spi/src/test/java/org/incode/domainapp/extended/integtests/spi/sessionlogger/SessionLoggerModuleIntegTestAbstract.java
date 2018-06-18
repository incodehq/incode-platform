package org.incode.domainapp.extended.integtests.spi.sessionlogger;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class SessionLoggerModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new SessionLoggerModuleIntegTestModule();
    }

    protected SessionLoggerModuleIntegTestAbstract() {
        super(module());
    }

}
