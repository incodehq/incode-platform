package org.incode.platform.spi.publishmq.integtests.app.fixtures;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

import org.incode.domainapp.example.dom.spi.publishmq.fixture.PublishMqDemoObjectsFixture;

//@DomainService(nature = NatureOfService.DOMAIN)
public class PublishMqDemoFixtureScriptsSpecificationProvider
        implements FixtureScriptsSpecificationProvider {

    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification
                .builder(PublishMqDemoFixtureScriptsSpecificationProvider.class)
                .with(FixtureScripts.MultipleExecutionStrategy.EXECUTE)
                .withRunScriptDefault(PublishMqDemoObjectsFixture.class)
                .withRunScriptDropDown(FixtureScriptsSpecification.DropDownPolicy.CHOICES)
                .withRecreate(PublishMqDemoObjectsFixture.class)
                .build();

    }
}
