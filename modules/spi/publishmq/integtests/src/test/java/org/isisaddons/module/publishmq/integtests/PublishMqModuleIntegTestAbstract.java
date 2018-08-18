package org.isisaddons.module.publishmq.integtests;

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
