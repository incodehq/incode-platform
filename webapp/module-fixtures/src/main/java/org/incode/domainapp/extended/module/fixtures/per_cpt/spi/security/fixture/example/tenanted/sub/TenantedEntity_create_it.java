package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub;

public class TenantedEntity_create_it extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it", "/it", executionContext);
    }

}
