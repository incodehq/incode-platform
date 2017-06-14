package org.isisaddons.module.security.fixture.scripts.example.tenanted;

public class FrTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /fr", "/fr", executionContext);
    }

}
