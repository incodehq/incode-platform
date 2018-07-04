package org.incode.domainapp.extended.integtests.ext.togglz;

import org.junit.Rule;
import org.togglz.junit.TogglzRule;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.incode.domainapp.extended.module.base.togglz.TogglzFeature;

public abstract class TogglzModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzFeature.class);

    public static ModuleAbstract module() {
        return new TogglzModuleIntegTestModule();
    }

    protected TogglzModuleIntegTestAbstract() {
        super(module());
    }


}
