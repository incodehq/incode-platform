package org.isisaddons.module.security.fixture.scripts.example.nontenanted;

public class BazNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Baz", executionContext);
    }

}
