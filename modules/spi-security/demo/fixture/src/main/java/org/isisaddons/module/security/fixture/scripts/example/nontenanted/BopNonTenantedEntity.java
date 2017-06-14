package org.isisaddons.module.security.fixture.scripts.example.nontenanted;

public class BopNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", executionContext);
    }

}
