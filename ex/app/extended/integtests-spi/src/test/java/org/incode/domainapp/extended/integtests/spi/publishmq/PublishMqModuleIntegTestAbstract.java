package org.incode.domainapp.extended.integtests.spi.publishmq;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class PublishMqModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new PublishMqModuleIntegTestModule();
    }

    protected PublishMqModuleIntegTestAbstract() {
        super(module());
    }

}
