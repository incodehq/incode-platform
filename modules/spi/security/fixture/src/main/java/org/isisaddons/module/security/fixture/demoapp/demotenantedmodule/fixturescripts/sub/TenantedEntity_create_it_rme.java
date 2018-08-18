package org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub;

public class TenantedEntity_create_it_rme extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /it/rme", "/it/rme", executionContext);
    }

}
