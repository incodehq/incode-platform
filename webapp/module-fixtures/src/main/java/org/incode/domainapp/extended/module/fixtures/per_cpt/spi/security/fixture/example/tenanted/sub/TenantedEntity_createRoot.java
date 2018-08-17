package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub;

public class TenantedEntity_createRoot extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /", "/", executionContext);
    }

}
