package org.isisaddons.module.command.integtests;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class CommandModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new CommandModuleIntegTestModule();
    }

    protected CommandModuleIntegTestAbstract() {
        super(module());
    }

}
