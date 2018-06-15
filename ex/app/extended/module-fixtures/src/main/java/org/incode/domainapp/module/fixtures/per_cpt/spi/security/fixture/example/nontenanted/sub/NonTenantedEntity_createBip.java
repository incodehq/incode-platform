package org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.example.nontenanted.sub;

public class NonTenantedEntity_createBip extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", executionContext);
    }

}
