package org.isisaddons.module.flywaydb.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;

import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObject;
import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObjectMenu;
import org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts.FlywayDbDemoObject_tearDown;
import org.isisaddons.module.flywaydb.integtests.FlywayDbModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Smoke_IntegTest extends FlywayDbModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;
    @Inject
    FlywayDbDemoObjectMenu menu;

    @Test
    public void create() throws Exception {

        // given
        fixtureScripts.runFixtureScript(new FlywayDbDemoObject_tearDown(), null);
        transactionService.nextTransaction();


        // when
        List<FlywayDbDemoObject> all = wrap(menu).listAllDemoObjects();

        // then
        assertThat(all).isEmpty();



        // when
        final FlywayDbDemoObject fred = wrap(menu).createDemoObject("Fred");
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAllDemoObjects();
        assertThat(all).hasSize(1);
        assertThat(all).contains(fred);



        // when
        final FlywayDbDemoObject bill = wrap(menu).createDemoObject("Bill");
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAllDemoObjects();
        assertThat(all).hasSize(2);
        assertThat(all).contains(fred, bill);



    }

}

