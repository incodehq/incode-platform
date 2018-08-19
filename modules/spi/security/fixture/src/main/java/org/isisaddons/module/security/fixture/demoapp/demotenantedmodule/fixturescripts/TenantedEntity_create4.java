package org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub.TenantedEntity_createRoot;
import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub.TenantedEntity_create_fr;
import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub.TenantedEntity_create_it;
import org.isisaddons.module.security.fixture.demoapp.demotenantedmodule.fixturescripts.sub.TenantedEntity_create_it_mil;

public class TenantedEntity_create4 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new TenantedEntity_create_fr());
        executionContext.executeChild(this, new TenantedEntity_create_it());
        executionContext.executeChild(this, new TenantedEntity_create_it_mil());
        executionContext.executeChild(this, new TenantedEntity_createRoot());

    }


}
