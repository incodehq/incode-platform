package org.isisaddons.module.security.fixture.scripts.example.tenanted;

public class RootTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /", "/", executionContext);
    }

}
