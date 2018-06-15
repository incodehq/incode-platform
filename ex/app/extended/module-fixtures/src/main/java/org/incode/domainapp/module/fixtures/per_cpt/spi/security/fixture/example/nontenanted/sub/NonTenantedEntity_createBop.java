package org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.example.nontenanted.sub;

public class NonTenantedEntity_createBop extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", executionContext);
    }

}
