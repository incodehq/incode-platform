package domainapp.modules.exampledom.spi.security.fixture.example.nontenanted;

import domainapp.modules.exampledom.spi.security.dom.demonontenanted.NonTenantedEntities;
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
