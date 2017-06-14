package org.isisaddons.module.fakedata.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

public abstract class FakeDataModuleIntegTest extends IntegrationTestAbstract {

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");
        FakeDataModuleSystemInitializer.initIsft();
        
        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
