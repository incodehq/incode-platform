package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub;

public class TenantedEntity_createNULL extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Null tenanted", null, executionContext);
    }

}
