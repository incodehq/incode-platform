package org.incode.domainapp.example.dom.spi.security.fixture.tenancy;

import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.AbstractTenancyFixtureScript;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_France;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_FranceLyon;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_FranceNice;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_FranceParis;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_Italy;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_ItalyMilan;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_ItalyRome;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_Sweden;
import org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub.ApplicationTenancy_create_SwedenStockholm;

public class ApplicationTenancy_create10 extends AbstractTenancyFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new GlobalTenancy());

        executionContext.executeChild(this, new ApplicationTenancy_create_Italy());
        executionContext.executeChild(this, new ApplicationTenancy_create_ItalyMilan());
        executionContext.executeChild(this, new ApplicationTenancy_create_ItalyRome());

        executionContext.executeChild(this, new ApplicationTenancy_create_France());
        executionContext.executeChild(this, new ApplicationTenancy_create_FranceParis());
        executionContext.executeChild(this, new ApplicationTenancy_create_FranceLyon());
        executionContext.executeChild(this, new ApplicationTenancy_create_FranceNice());

        executionContext.executeChild(this, new ApplicationTenancy_create_Sweden());
        executionContext.executeChild(this, new ApplicationTenancy_create_SwedenStockholm());
    }

}
