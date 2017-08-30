package domainapp.appdefn.bdd.specglue;

import org.apache.isis.core.specsupport.scenarios.ScenarioExecutionScope;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import domainapp.appdefn.integtests.DomainAppIntegTestAbstract;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BootstrappingGlue extends CukeGlueAbstract {

    @Before(value={"@integration"}, order=100)
    public void beforeScenarioIntegrationScope() {
        DomainAppIntegTestAbstract.initSystem();
        before(ScenarioExecutionScope.INTEGRATION);
    }

    @After
    public void afterScenario(cucumber.api.Scenario sc) {
        assertMocksSatisfied();
        after(sc);
    }
}
