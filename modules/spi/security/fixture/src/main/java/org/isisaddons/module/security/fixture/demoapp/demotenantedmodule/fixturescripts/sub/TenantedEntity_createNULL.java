package org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub;

public class TenantedEntity_createNULL extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Null tenanted", null, executionContext);
    }

}
