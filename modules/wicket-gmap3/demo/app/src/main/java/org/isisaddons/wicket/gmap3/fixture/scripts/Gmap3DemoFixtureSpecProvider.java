package org.isisaddons.wicket.gmap3.fixture.scripts;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

import domainapp.modules.exampledom.wicket.gmap3.ExampleDomWicketGmap3Module;
import domainapp.modules.exampledom.wicket.gmap3.fixture.Gmap3DemoSetUpFixture;

@DomainService()
public class Gmap3DemoFixtureSpecProvider implements FixtureScriptsSpecificationProvider {

    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification.builder(ExampleDomWicketGmap3Module.class)
                .withRunScriptDefault(Gmap3DemoSetUpFixture.class)
                .withRecreate(Gmap3DemoSetUpFixture.class)
                .build();
    }

}
