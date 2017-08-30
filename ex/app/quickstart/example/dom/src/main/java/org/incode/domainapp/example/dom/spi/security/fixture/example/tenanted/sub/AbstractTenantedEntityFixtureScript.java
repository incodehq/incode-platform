package org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyRepository;
import org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted.TenantedEntities;
import org.incode.domainapp.example.dom.spi.security.dom.demo.tenanted.TenantedEntity;

public abstract class AbstractTenantedEntityFixtureScript extends FixtureScript {

    protected TenantedEntity create(
            final String name,
            final String tenancyPath,
            final ExecutionContext executionContext) {
        final ApplicationTenancy tenancy = applicationTenancyRepository.findByPath(tenancyPath);
        final TenantedEntity entity = exampleTenantedEntities.createTenantedEntity(name, tenancy);
        executionContext.addResult(this, name, entity);
        return entity;
    }

    @javax.inject.Inject
    TenantedEntities exampleTenantedEntities;

    @javax.inject.Inject
    ApplicationTenancyRepository applicationTenancyRepository;

}
