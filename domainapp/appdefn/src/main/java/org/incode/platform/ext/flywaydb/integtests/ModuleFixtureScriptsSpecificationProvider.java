package org.incode.platform.ext.flywaydb.integtests;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

// @DomainService(nature = NatureOfService.DOMAIN)
public class ModuleFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification.builder("domainapp.modules.simple").with(
                FixtureScripts.MultipleExecutionStrategy.EXECUTE_ONCE_BY_VALUE).build();
    }
}
