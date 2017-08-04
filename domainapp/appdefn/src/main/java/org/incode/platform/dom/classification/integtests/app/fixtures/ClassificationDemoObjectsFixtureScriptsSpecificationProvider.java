package org.incode.platform.dom.classification.integtests.app.fixtures;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

import org.incode.domainapp.example.dom.dom.classification.fixture.ClassifiedDemoObjectsFixture;

/**
 * Specifies where to find fixtures, and other settings.
 */
//@DomainService(nature = NatureOfService.DOMAIN)
public class ClassificationDemoObjectsFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {

    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification
                .builder(ClassificationDemoObjectsFixtureScriptsSpecificationProvider.class)
                .with(FixtureScripts.MultipleExecutionStrategy.EXECUTE)
                .withRunScriptDefault(ClassifiedDemoObjectsFixture.class)
                .withRunScriptDropDown(FixtureScriptsSpecification.DropDownPolicy.CHOICES)
                .withRecreate(ClassifiedDemoObjectsFixture.class)
                .build();
    }
}
