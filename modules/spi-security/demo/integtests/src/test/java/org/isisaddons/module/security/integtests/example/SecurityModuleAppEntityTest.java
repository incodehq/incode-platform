package org.isisaddons.module.security.integtests.example;

import java.util.List;
import javax.inject.Inject;
import domainapp.modules.exampledom.spi.security.dom.demonontenanted.NonTenantedEntities;
import domainapp.modules.exampledom.spi.security.dom.demonontenanted.NonTenantedEntity;
import domainapp.modules.exampledom.spi.security.fixture.SecurityModuleAppTearDown;
import domainapp.modules.exampledom.spi.security.fixture.example.nontenanted.BarNonTenantedEntity;
import domainapp.modules.exampledom.spi.security.fixture.example.nontenanted.BazNonTenantedEntity;
import domainapp.modules.exampledom.spi.security.fixture.example.nontenanted.BipNonTenantedEntity;
import domainapp.modules.exampledom.spi.security.fixture.example.nontenanted.BopNonTenantedEntity;
import org.isisaddons.module.security.integtests.SecurityModuleAppIntegTest;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SecurityModuleAppEntityTest extends SecurityModuleAppIntegTest {

    NonTenantedEntity entity;

    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(
                new SecurityModuleAppTearDown(),
                new BipNonTenantedEntity(),
                new BarNonTenantedEntity(),
                new BazNonTenantedEntity(),
                new BopNonTenantedEntity()
                );
    }

    @Inject
    NonTenantedEntities exampleNonTenantedEntities;

    @Inject
    IsisJdoSupport isisJdoSupport;

    @Before
    public void setUp() throws Exception {
        final List<NonTenantedEntity> all = wrap(exampleNonTenantedEntities).listAll();
        assertThat(all.size(), is(4));

        entity = all.get(0);
    }

    public static class TODO extends SecurityModuleAppEntityTest {

        @Test
        public void TODO() throws Exception {

        }

    }


}