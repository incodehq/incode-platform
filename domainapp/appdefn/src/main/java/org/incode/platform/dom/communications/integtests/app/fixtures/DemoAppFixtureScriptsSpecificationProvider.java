package org.incode.platform.dom.communications.integtests.app.fixtures;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

//import org.incode.platform.dom.communications.integtests.app.fixtures.scenarios.DemoAppFixture;

/**
 * Specifies where to find fixtures, and other settings.
 */
//@DomainService(nature = NatureOfService.DOMAIN)
public class DemoAppFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification
                .builder(DemoAppFixtureScriptsSpecificationProvider.class)
                .with(FixtureScripts.MultipleExecutionStrategy.EXECUTE)
//                .withRunScriptDefault(DemoAppFixture.class)
                .withRunScriptDropDown(FixtureScriptsSpecification.DropDownPolicy.CHOICES)
//                .withRecreate(DemoAppFixture.class)
                .build();
    }
}
