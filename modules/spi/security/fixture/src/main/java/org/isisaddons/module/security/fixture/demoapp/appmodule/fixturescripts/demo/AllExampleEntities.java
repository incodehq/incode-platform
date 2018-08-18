package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.demo;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class AllExampleEntities extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new NonTenantedEntity_create4());
        executionContext.executeChild(this, new TenantedEntity_create4());

    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
