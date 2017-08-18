package org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.security.dom.demo.nontenanted.NonTenantedEntities;
import org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBar;
import org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBaz;
import org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBip;
import org.incode.domainapp.example.dom.spi.security.fixture.example.nontenanted.sub.NonTenantedEntity_createBop;

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
