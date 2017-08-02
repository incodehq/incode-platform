package org.incode.module.docfragment.demo.application.integtests;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

// TODO: reconcile with other, possibly incompatible, implementations...
// @DomainService(nature = NatureOfService.DOMAIN)
public class DocFragmentModuleFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification.builder("org.incode.module.docfragment").with(
                FixtureScripts.MultipleExecutionStrategy.EXECUTE_ONCE_BY_VALUE).build();
    }
}
