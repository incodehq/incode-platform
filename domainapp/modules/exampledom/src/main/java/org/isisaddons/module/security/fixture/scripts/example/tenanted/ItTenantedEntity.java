package org.isisaddons.module.security.fixture.scripts.example.tenanted;

public class ItTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it", "/it", executionContext);
    }

}
