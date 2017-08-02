package domainapp.modules.exampledom.spi.security.fixture.example.nontenanted;

import domainapp.modules.exampledom.spi.security.dom.demonontenanted.NonTenantedEntities;
import domainapp.modules.exampledom.spi.security.dom.demonontenanted.NonTenantedEntity;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class AbstractNonTenantedEntityFixtureScript extends FixtureScript {

    protected NonTenantedEntity create(
            final String name,
            final ExecutionContext executionContext) {
        final NonTenantedEntity entity = exampleNonTenantedEntities.create(name);
        executionContext.addResult(this, name, entity);
        return entity;
    }

    @javax.inject.Inject
    private NonTenantedEntities exampleNonTenantedEntities;

}
