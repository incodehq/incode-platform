package domainapp.appdefn.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.togglz.junit.TogglzRule;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.xactn.TransactionService;

import domainapp.appdefn.fixture.teardown.DomainAppTearDown;
import domainapp.modules.base.togglz.TogglzFeature;
import domainapp.modules.simple.dom.SimpleObject;
import domainapp.modules.simple.dom.SimpleObjectMenu;
import static org.assertj.core.api.Assertions.assertThat;

public class Smoke_IntegTest extends DomainAppIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;
    @Inject
    SimpleObjectMenu menu;

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TogglzFeature.class);


    @Test
    public void create() throws Exception {

        // given
        DomainAppTearDown fs = new DomainAppTearDown();
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();


        // when
        List<SimpleObject> all = wrap(menu).listAll();

        // then
        assertThat(all).isEmpty();



        // when
        final SimpleObject fred = wrap(menu).create("Fred");
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);
        assertThat(all).contains(fred);



        // when
        final SimpleObject bill = wrap(menu).create("Bill");
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(2);
        assertThat(all).contains(fred, bill);



        // when
        wrap(fred).updateName("Freddy");
        transactionService.flushTransaction();

        // then
        assertThat(wrap(fred).getName()).isEqualTo("Freddy");



        // when
        wrap(fred).setNotes("These are some notes");

        // then
        assertThat(wrap(fred).getNotes()).isEqualTo("These are some notes");


        // when
        wrap(fred).delete();
        transactionService.flushTransaction();


        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);

    }

}

