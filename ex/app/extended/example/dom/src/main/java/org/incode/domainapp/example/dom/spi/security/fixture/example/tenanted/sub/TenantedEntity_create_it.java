package org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub;

import org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub.AbstractTenantedEntityFixtureScript;

public class TenantedEntity_create_it extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it", "/it", executionContext);
    }

}
