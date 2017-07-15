package domainapp.application.bdd.specglue;

import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import cucumber.api.java.Before;
import domainapp.modules.exampledom.ext.flywaydb.fixture.scenario.RecreateFlywayDemoObjects;

public class CatalogOfFixturesGlue extends CukeGlueAbstract {

    @Before(value={"@integration", "@RecreateFlywayDemoObjects"}, order=20000)
    public void integrationFixtures() throws Throwable {
        scenarioExecution().install(new RecreateFlywayDemoObjects());
    }

}
