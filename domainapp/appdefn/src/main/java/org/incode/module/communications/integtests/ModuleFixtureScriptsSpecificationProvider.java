package org.incode.module.communications.integtests;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

//@DomainService(nature = NatureOfService.DOMAIN)
public class ModuleFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification.builder("org.incode.module.communications").with(
                FixtureScripts.MultipleExecutionStrategy.EXECUTE_ONCE_BY_VALUE).build();
    }
}
