package org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntity;

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
