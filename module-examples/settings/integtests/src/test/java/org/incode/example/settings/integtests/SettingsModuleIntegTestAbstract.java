package org.incode.example.settings.integtests;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class SettingsModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new SettingsModuleIntegTestModule();
    }

    protected SettingsModuleIntegTestAbstract() {
        super(module());
    }

}
