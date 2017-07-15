package domainapp.modules.exampledom.ext.flywaydb.integtests.tests;

import java.sql.Timestamp;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;
import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusIdLong;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusVersionTimestamp;

import domainapp.modules.exampledom.ext.flywaydb.dom.FlywayDemoObject;
import domainapp.modules.exampledom.ext.flywaydb.fixture.scenario.RecreateFlywayDemoObjects;
import domainapp.modules.exampledom.ext.flywaydb.integtests.SimpleModuleIntegTestAbstract;
import static org.assertj.core.api.Assertions.assertThat;

public class FlywayDemoObject_IntegTest extends SimpleModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    TransactionService transactionService;

    FlywayDemoObject flywayDemoObject;

    @Before
    public void setUp() throws Exception {
        // given
        RecreateFlywayDemoObjects fs = new RecreateFlywayDemoObjects().setNumber(1);
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();

        flywayDemoObject = fs.getFlywayDemoObjects().get(0);

        assertThat(flywayDemoObject).isNotNull();
    }

    public static class Name extends FlywayDemoObject_IntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String name = wrap(flywayDemoObject).getName();

            // then
            assertThat(name).isEqualTo(flywayDemoObject.getName());
        }

        @Test
        public void not_editable() throws Exception {
            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            wrap(flywayDemoObject).setName("new name");
        }

    }

    public static class UpdateName extends FlywayDemoObject_IntegTest {

        @Test
        public void can_be_updated_directly() throws Exception {

            // when
            wrap(mixin(FlywayDemoObject.updateName.class, flywayDemoObject)).exec("new name");
            transactionService.nextTransaction();

            // then
            assertThat(wrap(flywayDemoObject).getName()).isEqualTo("new name");
        }

        @Test
        public void failsValidation() throws Exception {

            // expect
            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("Exclamation mark is not allowed");

            // when
            wrap(mixin(FlywayDemoObject.updateName.class, flywayDemoObject)).exec("new name!");
        }
    }


    public static class Title extends FlywayDemoObject_IntegTest {

        @Inject
        TitleService titleService;

        @Test
        public void interpolatesName() throws Exception {

            // given
            final String name = wrap(flywayDemoObject).getName();

            // when
            final String title = titleService.titleOf(flywayDemoObject);

            // then
            assertThat(title).isEqualTo("Object: " + name);
        }
    }

    public static class DataNucleusId extends FlywayDemoObject_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Long id = mixin(Persistable_datanucleusIdLong.class, flywayDemoObject).exec();

            // then
            assertThat(id).isGreaterThanOrEqualTo(0);
        }
    }

    public static class DataNucleusVersionTimestamp extends FlywayDemoObject_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Timestamp timestamp = mixin(Persistable_datanucleusVersionTimestamp.class, flywayDemoObject).exec();
            // then
            assertThat(timestamp).isNotNull();
        }
    }


}