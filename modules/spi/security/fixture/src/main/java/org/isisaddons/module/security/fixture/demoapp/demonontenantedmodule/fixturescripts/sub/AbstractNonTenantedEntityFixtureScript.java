package org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntities;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntity;

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
