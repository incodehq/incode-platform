package org.isisaddons.module.excel.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

public abstract class ExcelModuleModuleIntegTest extends IntegrationTestAbstract {

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");
        ExcelModuleModuleSystemInitializer.initIsft();
        
        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
