package org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub;

import org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.example.tenanted.sub.AbstractTenantedEntityFixtureScript;

public class TenantedEntity_create_it_mil extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it/mil", "/it/mil", executionContext);
    }

}
