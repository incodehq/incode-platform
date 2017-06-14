package org.isisaddons.module.stringinterpolator.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

public abstract class StringInterpolatorDemoIntegTest extends IntegrationTestAbstract {

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging.properties");
        StringInterpolatorDemoSystemInitializer.initIsft();

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
