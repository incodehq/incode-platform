package org.isisaddons.module.security.fixture.scripts.example.nontenanted;

import org.isisaddons.module.security.fixture.dom.example.nontenanted.NonTenantedEntities;
import org.isisaddons.module.security.fixture.dom.example.nontenanted.NonTenantedEntity;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class AbstractNonTenantedEntityFixtureScript extends FixtureScript {

    protected NonTenantedEntity create(
            final String name,
            final ExecutionContext executionContext) {
        final NonTenantedEntity entity = exampleNonTenantedEntities.create(name);
        executionContext.addResult(this, name, entity);
        return entity;
    }

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
