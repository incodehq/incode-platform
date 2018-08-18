package org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub;

public class NonTenantedEntity_createBip extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", executionContext);
    }

}
