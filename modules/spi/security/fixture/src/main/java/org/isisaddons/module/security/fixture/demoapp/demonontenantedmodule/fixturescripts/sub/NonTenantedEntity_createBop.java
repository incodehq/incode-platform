package org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub;

public class NonTenantedEntity_createBop extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bop", executionContext);
    }

}
