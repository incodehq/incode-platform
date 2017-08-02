package domainapp.modules.exampledom.spi.security.fixture.example;

import domainapp.modules.exampledom.spi.security.dom.demonontenanted.NonTenantedEntities;
import domainapp.modules.exampledom.spi.security.fixture.example.nontenanted.AllExampleNonTenantedEntities;
import domainapp.modules.exampledom.spi.security.fixture.example.tenanted.AllExampleTenantedEntities;
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
