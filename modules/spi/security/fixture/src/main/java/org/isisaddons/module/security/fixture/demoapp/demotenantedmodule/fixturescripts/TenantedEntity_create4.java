package org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class TenantedEntity_create4 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new TenantedEntity_create_fr());
        executionContext.executeChild(this, new TenantedEntity_create_it());
        executionContext.executeChild(this, new TenantedEntity_create_it_mil());
        executionContext.executeChild(this, new TenantedEntity_createRoot());

    }


}
