package org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted;

public class FrTenantedEntity extends AbstractTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Tenanted in /fr", "/fr", executionContext);
    }

}
