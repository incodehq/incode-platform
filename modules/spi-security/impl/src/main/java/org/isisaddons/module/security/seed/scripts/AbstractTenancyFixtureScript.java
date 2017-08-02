package org.isisaddons.module.security.seed.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyRepository;

public abstract class AbstractTenancyFixtureScript extends FixtureScript {


    protected ApplicationTenancy create(
            final String name,
            final String path,
            final String parentPath,
            final ExecutionContext executionContext) {

        final ApplicationTenancy parent = parentPath != null? applicationTenancyRepository.findByPath(parentPath): null;
        applicationTenancy = applicationTenancyRepository.newTenancy(name, path, parent);
        executionContext.addResult(this, name, applicationTenancy);
        return applicationTenancy;
    }

    private ApplicationTenancy applicationTenancy;

    /**
     * The {@link org.isisaddons.module.security.dom.tenancy.ApplicationTenancy} created by this fixture.
     */
    public ApplicationTenancy getApplicationTenancy() {
        return applicationTenancy;
    }

    @javax.inject.Inject
    private ApplicationTenancyRepository applicationTenancyRepository;

}
