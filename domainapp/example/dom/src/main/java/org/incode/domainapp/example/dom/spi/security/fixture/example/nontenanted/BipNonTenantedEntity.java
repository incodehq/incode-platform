package org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted;

public class BipNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bip", executionContext);
    }

}
