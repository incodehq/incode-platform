package org.incode.platform.spi.command.integtests.app.fixtures;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

import org.incode.domainapp.example.dom.spi.command.fixture.SomeCommandAnnotatedObjectsFixture;

@DomainService(nature = NatureOfService.DOMAIN)
public class SomeCommandAnnotatedObjectsFixtureScriptsSpecificationProvider
        implements FixtureScriptsSpecificationProvider {

    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification
                .builder(SomeCommandAnnotatedObjectsFixtureScriptsSpecificationProvider.class)
                .with(FixtureScripts.MultipleExecutionStrategy.EXECUTE)
                .withRunScriptDefault(SomeCommandAnnotatedObjectsFixture.class)
                .withRunScriptDropDown(FixtureScriptsSpecification.DropDownPolicy.CHOICES)
                .withRecreate(SomeCommandAnnotatedObjectsFixture.class)
                .build();

    }
}
