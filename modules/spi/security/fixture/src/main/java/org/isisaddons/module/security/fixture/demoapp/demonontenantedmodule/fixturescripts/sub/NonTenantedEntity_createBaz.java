package org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub;

public class NonTenantedEntity_createBaz extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Baz", executionContext);
    }

}
