package org.isisaddons.module.security.integtests.example;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.security.fixture.dom.example.nontenanted.NonTenantedEntities;
import org.isisaddons.module.security.fixture.dom.example.nontenanted.NonTenantedEntity;
import org.isisaddons.module.security.fixture.scripts.SecurityModuleAppTearDown;
import org.isisaddons.module.security.fixture.scripts.example.nontenanted.BarNonTenantedEntity;
import org.isisaddons.module.security.fixture.scripts.example.nontenanted.BazNonTenantedEntity;
import org.isisaddons.module.security.fixture.scripts.example.nontenanted.BipNonTenantedEntity;
import org.isisaddons.module.security.fixture.scripts.example.nontenanted.BopNonTenantedEntity;
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