package org.incode.domainapp.extended.integtests.lib.stringinterpolator;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

public abstract class StringInterpolatorModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new StringInterpolatorModuleIntegTestModule();
    }

    protected StringInterpolatorModuleIntegTestAbstract() {
        super(module());
    }


}
