package org.isisaddons.module.security.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

public abstract class SecurityModuleAppIntegTest extends IntegrationTestAbstract {

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");
        SecurityModuleAppSystemInitializer.initIsft();
        
        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
