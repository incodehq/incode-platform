package domainapp.application.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;

import domainapp.application.fixture.teardown.DomainAppTearDown;
import domainapp.modules.simple.dom.impl.FlywayDemoObject;
import domainapp.modules.simple.dom.impl.FlywayDemoObjectMenu;
import static org.assertj.core.api.Assertions.assertThat;

public class Smoke_IntegTest extends DomainAppIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;
    @Inject
    FlywayDemoObjectMenu menu;

    @Test
    public void create() throws Exception {

        // given
        DomainAppTearDown fs = new DomainAppTearDown();
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();


        // when
        List<FlywayDemoObject> all = wrap(menu).listAll();

        // then
        assertThat(all).isEmpty();



        // when
        final FlywayDemoObject fred = wrap(menu).create("Fred");
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);
        assertThat(all).contains(fred);



        // when
        final FlywayDemoObject bill = wrap(menu).create("Bill");
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(2);
        assertThat(all).contains(fred, bill);



        // when
        wrap(mixin(FlywayDemoObject.updateName.class, fred)).exec("Freddy");
        transactionService.flushTransaction();

        // then
        assertThat(wrap(fred).getName()).isEqualTo("Freddy");



        // when
        wrap(fred).setNotes("These are some notes");

        // then
        assertThat(wrap(fred).getNotes()).isEqualTo("These are some notes");


        // when
        wrap(mixin(FlywayDemoObject.delete.class, fred)).exec();
        transactionService.flushTransaction();


        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);

    }

}

