package org.incode.domainapp.example.app.fixtures;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

@DomainService(nature = NatureOfService.DOMAIN)
public class FixtureSpecProvider implements FixtureScriptsSpecificationProvider {

    @Override public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification.builder("org.incode.domainapp.example")
                .with(FixtureScripts.MultipleExecutionStrategy.EXECUTE)
                .with(FixtureScripts.NonPersistedObjectsStrategy.PERSIST)
                .withRunScriptDefault(RecreateDemoFixtures.class)
                .withRecreate(RecreateDemoFixtures.class)
                .withRunScriptDropDown(FixtureScriptsSpecification.DropDownPolicy.CHOICES)
                .build();
    }

}
