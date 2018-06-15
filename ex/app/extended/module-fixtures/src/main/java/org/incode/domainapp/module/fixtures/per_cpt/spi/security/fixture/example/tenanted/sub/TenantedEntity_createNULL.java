package org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub;

import org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub.AbstractTenantedEntityFixtureScript;

public class TenantedEntity_createNULL extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Null tenanted", null, executionContext);
    }

}
