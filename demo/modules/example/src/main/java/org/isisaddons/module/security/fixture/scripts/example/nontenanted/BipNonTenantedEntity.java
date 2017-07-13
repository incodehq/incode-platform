package org.isisaddons.module.security.fixture.scripts.example.nontenanted;

public class BipNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", executionContext);
    }

}
