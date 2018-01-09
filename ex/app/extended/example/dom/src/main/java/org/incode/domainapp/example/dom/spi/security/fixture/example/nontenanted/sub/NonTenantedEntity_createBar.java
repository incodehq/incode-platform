package org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted.sub;

public class NonTenantedEntity_createBar extends AbstractNonTenantedEntityFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        create("Bar", executionContext);
    }

}
