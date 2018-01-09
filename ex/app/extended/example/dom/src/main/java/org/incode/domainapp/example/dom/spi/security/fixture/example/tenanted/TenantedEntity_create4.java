package org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub.TenantedEntity_createRoot;
import org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub.TenantedEntity_create_fr;
import org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub.TenantedEntity_create_it;
import org.incode.domainapp.example.dom.spi.security.fixture.example.tenanted.sub.TenantedEntity_create_it_mil;

public class TenantedEntity_create4 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new TenantedEntity_create_fr());
        executionContext.executeChild(this, new TenantedEntity_create_it());
        executionContext.executeChild(this, new TenantedEntity_create_it_mil());
        executionContext.executeChild(this, new TenantedEntity_createRoot());

    }


}
