package org.isisaddons.module.poly.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demofixedasset.PolyDemoFixedAsset_create6;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannels;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.fixedasset.PolyDemoFixedAsset;
import org.isisaddons.module.poly.integtests.PolyModuleIntegTestAbstract;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FixedAsset_IntegTest extends PolyModuleIntegTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    PolyDemoCommunicationChannels communicationChannelsMenu;

    PolyDemoFixedAsset_create6 fs;
    PolyDemoFixedAsset fixedAsset;

    @Before
    public void setUp() throws Exception {

        fs = new PolyDemoFixedAsset_create6();
        fixtureScripts.runFixtureScript(fs, null);
        transactionService.nextTransaction();

        fixedAsset = fs.getFixedAssets().get(0);
    }

    public static class CreateCommunicationChannel extends FixedAsset_IntegTest {


        @Test
        public void happyCase() throws Exception {

            // when
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            transactionService.nextTransaction();

            // then
            final List<PolyDemoCommunicationChannel> all = communicationChannelsMenu.listAllCommunicationChannels();
            assertThat(all.size(), is(1));

            final PolyDemoCommunicationChannel communicationChannel = fixedAsset.getCommunicationChannel();
            assertThat(communicationChannel.getDetails(), is("0207 123 4567"));
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            transactionService.nextTransaction();

            // then expect
            expectedException.expect(DisabledException.class);
            expectedException.expectMessage("Already owns a communication channel");

            // when
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            transactionService.nextTransaction();
        }

        @Test
        public void whenFailsValidation() throws Exception {

            // then expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("No exclamation marks allowed in details");

            // when
            wrap(fixedAsset).createCommunicationChannel("&!%$");
        }
    }

    public static class DeleteCommunicationChannel extends FixedAsset_IntegTest {

        @Test
        public void whenExists() throws Exception {

            // given
            wrap(fixedAsset).createCommunicationChannel("0207 123 4567");
            transactionService.nextTransaction();

            // when
            wrap(fixedAsset).deleteCommunicationChannel();
            transactionService.nextTransaction();

            // then
            assertThat(wrap(fixedAsset).getCommunicationChannel(), is(nullValue()));
            assertThat(wrap(communicationChannelsMenu).listAllCommunicationChannels().size(), is(0));
        }

        @Test
        public void whenDoesNotExist() throws Exception {

            // then expect
            expectedException.expect(DisabledException.class);
            expectedException.expectMessage("Does not own a communication channel");

            // when
            wrap(fixedAsset).deleteCommunicationChannel();
        }
    }

}