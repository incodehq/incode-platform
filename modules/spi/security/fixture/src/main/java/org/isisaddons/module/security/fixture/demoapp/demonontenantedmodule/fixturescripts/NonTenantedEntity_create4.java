package org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.dom.NonTenantedEntities;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub.NonTenantedEntity_createBar;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub.NonTenantedEntity_createBaz;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub.NonTenantedEntity_createBip;
import org.isisaddons.module.security.fixture.demoapp.demonontenantedmodule.fixturescripts.sub.NonTenantedEntity_createBop;

public class NonTenantedEntity_create4 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new NonTenantedEntity_createBip());
        executionContext.executeChild(this, new NonTenantedEntity_createBar());
        executionContext.executeChild(this, new NonTenantedEntity_createBaz());
        executionContext.executeChild(this, new NonTenantedEntity_createBop());

    }

    @javax.inject.Inject
    NonTenantedEntities exampleNonTenantedEntities;

}
