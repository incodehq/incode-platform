package org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted;

import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntity;
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
