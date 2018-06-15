package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub.AbstractTenantedEntityFixtureScript;

public class TenantedEntity_create_fr extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /fr", "/fr", executionContext);
    }

}
