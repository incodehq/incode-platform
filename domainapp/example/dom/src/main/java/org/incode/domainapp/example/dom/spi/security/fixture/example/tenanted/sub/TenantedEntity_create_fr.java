package org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub;

import org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub.AbstractTenantedEntityFixtureScript;

public class TenantedEntity_create_fr extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /fr", "/fr", executionContext);
    }

}
