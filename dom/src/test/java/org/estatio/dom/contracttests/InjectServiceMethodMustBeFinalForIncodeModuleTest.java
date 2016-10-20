package org.estatio.dom.contracttests;

import org.apache.isis.core.unittestsupport.inject.InjectServiceMethodMustBeFinalContractTestAbstract;

public class InjectServiceMethodMustBeFinalForIncodeModuleTest extends InjectServiceMethodMustBeFinalContractTestAbstract {

    public InjectServiceMethodMustBeFinalForIncodeModuleTest() {
        super("org.incode.module");
        withLoggingTo(System.out);
    }

}
