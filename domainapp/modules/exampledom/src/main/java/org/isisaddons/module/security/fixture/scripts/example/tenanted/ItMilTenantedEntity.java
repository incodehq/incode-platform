package org.isisaddons.module.security.fixture.scripts.example.tenanted;

public class ItMilTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it/mil", "/it/mil", executionContext);
    }

}
