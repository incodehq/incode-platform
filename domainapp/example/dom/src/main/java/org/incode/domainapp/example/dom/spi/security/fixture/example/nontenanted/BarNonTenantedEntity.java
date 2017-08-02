package org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted;

public class BarNonTenantedEntity extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bar", executionContext);
    }

}
