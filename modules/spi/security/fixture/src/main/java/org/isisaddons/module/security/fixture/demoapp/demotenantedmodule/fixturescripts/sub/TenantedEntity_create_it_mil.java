package org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub;

public class TenantedEntity_create_it_mil extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it/mil", "/it/mil", executionContext);
    }

}
