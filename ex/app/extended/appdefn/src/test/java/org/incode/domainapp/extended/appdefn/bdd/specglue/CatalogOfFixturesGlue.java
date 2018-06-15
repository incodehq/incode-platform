package org.incode.domainapp.extended.appdefn.bdd.specglue;

import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

import cucumber.api.java.Before;
import org.incode.domainapp.extended.appdefn.fixture.scenarios.DomainAppDemo;

public class CatalogOfFixturesGlue extends CukeGlueAbstract {

    @Before(value={"@integration", "@DomainAppDemo"}, order=20000)
    public void integrationFixtures() throws Throwable {
        scenarioExecution().install(new DomainAppDemo());
    }

}
