package org.isisaddons.module.security.fixture.scripts.example.nontenanted;

public class BarNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bar", executionContext);
    }

}
