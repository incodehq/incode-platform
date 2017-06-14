package org.isisaddons.module.togglz.integtests;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.togglz.junit.TogglzRule;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

import org.isisaddons.module.togglz.fixture.dom.module.featuretoggle.TogglzDemoFeature;

public abstract class TogglzModuleIntegTest extends IntegrationTestAbstract {

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzDemoFeature.class);

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging.properties");
        TogglzModuleSystemInitializer.initIsft();
        
        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
