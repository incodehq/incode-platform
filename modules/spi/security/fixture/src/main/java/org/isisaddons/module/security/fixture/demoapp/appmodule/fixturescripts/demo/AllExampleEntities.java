package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.demo;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntities;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.NonTenantedEntity_create4;
import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.TenantedEntity_create4;

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
