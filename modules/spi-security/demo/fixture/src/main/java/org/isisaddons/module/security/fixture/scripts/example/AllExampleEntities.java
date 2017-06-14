package org.isisaddons.module.security.fixture.scripts.example;

import org.isisaddons.module.security.fixture.dom.example.nontenanted.NonTenantedEntities;
import org.isisaddons.module.security.fixture.scripts.example.nontenanted.AllExampleNonTenantedEntities;
import org.isisaddons.module.security.fixture.scripts.example.tenanted.AllExampleTenantedEntities;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class AllExampleEntities extends DiscoverableFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new AllExampleNonTenantedEntities());
        executionContext.executeChild(this, new AllExampleTenantedEntities());

    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
