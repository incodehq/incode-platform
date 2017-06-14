package org.isisaddons.module.security.fixture.scripts.example.tenanted;

public class NullTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Null tenanted", null, executionContext);
    }

}
