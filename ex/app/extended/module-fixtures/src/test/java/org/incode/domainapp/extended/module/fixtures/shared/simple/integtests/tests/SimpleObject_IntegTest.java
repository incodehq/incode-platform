package org.incode.domainapp.extended.module.fixtures.shared.simple.integtests.tests;

import java.sql.Timestamp;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusIdLong;
import org.apache.isis.core.metamodel.services.jdosupport.Persistable_datanucleusVersionTimestamp;

import org.incode.domainapp.extended.module.fixtures.shared.simple.dom.SimpleObject;
import org.incode.domainapp.extended.module.fixtures.shared.simple.fixture.SimpleObject_createUpTo10;
import org.incode.domainapp.extended.module.fixtures.shared.simple.fixture.data.SimpleObject_data;
import org.incode.domainapp.extended.module.fixtures.shared.simple.fixture.SimpleObject_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.simple.integtests.SimpleModuleIntegTestAbstract;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObject_IntegTest extends SimpleModuleIntegTestAbstract {


    SimpleObject simpleObject;

    @Before
    public void setUp() throws Exception {
        // given
        fixtureScripts.runFixtureScript(new SimpleObject_tearDown(), null);
        SimpleObject_createUpTo10 fs = new SimpleObject_createUpTo10().setNumber(10);
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();

        simpleObject = fakeDataService.enums().anyOf(SimpleObject_data.class).findUsing(serviceRegistry);

        assertThat(simpleObject).isNotNull();
    }

    public static class Name extends SimpleObject_IntegTest {

        @Test
        public void accessible() throws Exception {
            // when
            final String name = wrap(simpleObject).getName();

            // then
            assertThat(name).isEqualTo(simpleObject.getName());
        }

        @Test
        public void not_editable() throws Exception {
            // expect
            expectedExceptions.expect(DisabledException.class);

            // when
            wrap(simpleObject).setName("new name");
        }

    }

    public static class UpdateName extends SimpleObject_IntegTest {

        @Test
        public void can_be_updated_directly() throws Exception {

            // when
            wrap(simpleObject).updateName("new name");
            transactionService.nextTransaction();

            // then
            assertThat(wrap(simpleObject).getName()).isEqualTo("new name");
        }

        @Test
        public void failsValidation() throws Exception {

            // expect
            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("Exclamation mark is not allowed");

            // when
            wrap(simpleObject).updateName("new name!");
        }
    }


    public static class Title extends SimpleObject_IntegTest {

        @Inject
        TitleService titleService;

        @Test
        public void interpolatesName() throws Exception {

            // given
            final String name = wrap(simpleObject).getName();

            // when
            final String title = titleService.titleOf(simpleObject);

            // then
            assertThat(title).isEqualTo("Object: " + name);
        }
    }

    public static class DataNucleusId extends SimpleObject_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Long id = mixin(Persistable_datanucleusIdLong.class, simpleObject).prop();

            // then
            assertThat(id).isGreaterThanOrEqualTo(0);
        }
    }

    public static class DataNucleusVersionTimestamp extends SimpleObject_IntegTest {

        @Test
        public void should_be_populated() throws Exception {
            // when
            final Timestamp timestamp = mixin(Persistable_datanucleusVersionTimestamp.class, simpleObject).prop();
            // then
            assertThat(timestamp).isNotNull();
        }
    }


}