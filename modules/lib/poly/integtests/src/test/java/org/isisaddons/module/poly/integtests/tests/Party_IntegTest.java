package org.isisaddons.module.poly.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demoparty.PolyDemoParty_create3;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannels;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty;
import org.isisaddons.module.poly.integtests.PolyModuleIntegTestAbstract;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Party_IntegTest extends PolyModuleIntegTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    PolyDemoCommunicationChannels communicationChannelsMenu;

    public static class AddCommunicationChannel extends Party_IntegTest {

        private PolyDemoParty_create3 fs;
        private PolyDemoParty party;

        @Before
        public void setUp() throws Exception {

            // given
            fs = new PolyDemoParty_create3();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            party = fs.getParties().get(0);
        }

        @Test
        public void addFirst() throws Exception {

            // when
            wrap(party).addCommunicationChannel("0207 123 4567");

            // then
            List<PolyDemoCommunicationChannel> parties = party.getCommunicationChannels();
            assertThat(parties.size(), is(1));
            assertThat(parties.get(0).getDetails(), is("0207 123 4567"));

            parties = communicationChannelsMenu.listAllCommunicationChannels();
            assertThat(parties.size(), is(1));
        }

        @Test
        public void addSecond() throws Exception {

            // given
            wrap(party).addCommunicationChannel("0207 123 4567");

            // when
            wrap(party).addCommunicationChannel("0207 765 4321");

            // when
            List<PolyDemoCommunicationChannel> parties = party.getCommunicationChannels();
            assertThat(parties.size(), is(2));
            assertThat(parties.get(0).getDetails(), is("0207 123 4567"));
            assertThat(parties.get(1).getDetails(), is("0207 765 4321"));
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            wrap(party).addCommunicationChannel("0207 123 4567");

            // then expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("Already have a communication channel with those details");

            // when
            wrap(party).addCommunicationChannel("0207 123 4567");
            transactionService.nextTransaction();
        }
    }

    public static class RemoveCommunicationChannel extends Party_IntegTest {

        private PolyDemoParty_create3 fs;
        private PolyDemoParty party;

        @Before
        public void setUp() throws Exception {

            // given
            fs = new PolyDemoParty_create3();
            fixtureScripts.runFixtureScript(fs, null);
            transactionService.nextTransaction();

            party = fs.getParties().get(0);

            wrap(party).addCommunicationChannel("0207 123 4567");
            wrap(party).addCommunicationChannel("0207 765 4321");
        }

        @Test
        public void whenExists() throws Exception {

            // given
            List<PolyDemoCommunicationChannel> communicationChannels = party.getCommunicationChannels();
            assertThat(communicationChannels.size(), is(2));
            final PolyDemoCommunicationChannel communicationChannel = communicationChannels.get(0);

            // when
            wrap(party).removeCommunicationChannel(communicationChannel);
            transactionService.nextTransaction();

            // then
            communicationChannels = party.getCommunicationChannels();
            assertThat(communicationChannels.size(), is(1));
        }
   }

}