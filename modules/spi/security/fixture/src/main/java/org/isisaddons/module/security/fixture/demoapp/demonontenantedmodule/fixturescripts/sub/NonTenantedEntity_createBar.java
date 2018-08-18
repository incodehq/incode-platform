package org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub;

public class NonTenantedEntity_createBar extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bar", executionContext);
    }

}
