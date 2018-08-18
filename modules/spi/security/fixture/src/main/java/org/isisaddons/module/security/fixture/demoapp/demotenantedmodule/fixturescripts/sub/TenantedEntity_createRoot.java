package org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub;

public class TenantedEntity_createRoot extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /", "/", executionContext);
    }

}
