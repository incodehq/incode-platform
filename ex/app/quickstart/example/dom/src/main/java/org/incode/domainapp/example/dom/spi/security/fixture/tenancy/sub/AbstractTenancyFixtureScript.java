package org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub;

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
        final ApplicationTenancy tenancy = applicationTenancyRepository.newTenancy(name, path, parent);
        executionContext.addResult(this, name, tenancy);
        return tenancy;
    }

    @javax.inject.Inject
    private ApplicationTenancyRepository applicationTenancyRepository;

}
