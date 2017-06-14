package org.isisaddons.metamodel.paraname8.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

public abstract class Paraname8ModuleIntegTest extends IntegrationTestAbstract {

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging.properties");
        Paraname8ModuleSystemInitializer.initIsft();
        
        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
