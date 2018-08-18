package org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class AbstractNonTenantedEntityFixtureScript extends FixtureScript {

    protected NonTenantedEntity create(
            final String name,
            final ExecutionContext executionContext) {
        final NonTenantedEntity entity = exampleNonTenantedEntities.createNonTenantedEntity(name);
        executionContext.addResult(this, name, entity);
        return entity;
    }

    @javax.inject.Inject
    NonTenantedEntities exampleNonTenantedEntities;

}
