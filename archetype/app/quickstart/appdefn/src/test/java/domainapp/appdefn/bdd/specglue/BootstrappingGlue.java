package domainapp.appdefn.bdd.specglue;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;
import org.apache.isis.core.specsupport.scenarios.ScenarioExecutionScope;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import domainapp.appdefn.DomainAppAppManifestNoFlywayDb;

public class BootstrappingGlue extends CukeGlueAbstract {

    @Before(value={"@integration"}, order=100)
    public void beforeScenarioIntegrationScope() {
        new IntegrationTestAbstract2() {
            void bootstrap() {
                IntegrationTestAbstract2.bootstrapUsing(
                        DomainAppAppManifestNoFlywayDb.BUILDER.build());
            }
        }.bootstrap();
        before(ScenarioExecutionScope.INTEGRATION);
    }

    @After
    public void afterScenario(cucumber.api.Scenario sc) {
        assertMocksSatisfied();
        after(sc);
    }
}
