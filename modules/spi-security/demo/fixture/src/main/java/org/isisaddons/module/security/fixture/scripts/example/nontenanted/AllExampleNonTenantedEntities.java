package org.isisaddons.module.security.fixture.scripts.example.nontenanted;

import org.isisaddons.module.security.fixture.dom.example.nontenanted.NonTenantedEntities;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class AllExampleNonTenantedEntities extends DiscoverableFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new BipNonTenantedEntity());
        executionContext.executeChild(this, new BarNonTenantedEntity());
        executionContext.executeChild(this, new BazNonTenantedEntity());
        executionContext.executeChild(this, new BopNonTenantedEntity());

    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
