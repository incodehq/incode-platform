package org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub;

import org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub.AbstractTenantedEntityFixtureScript;

public class TenantedEntity_createNULL extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Null tenanted", null, executionContext);
    }

}
