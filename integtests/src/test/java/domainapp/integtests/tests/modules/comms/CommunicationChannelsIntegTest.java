/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.integtests.tests.modules.comms;

import domainapp.dom.modules.comms.CommunicationChannel;
import domainapp.dom.modules.comms.CommunicationChannelsContributions;
import domainapp.dom.modules.comms.CommunicationChannelsMenu;
import domainapp.dom.modules.party.Party;
import domainapp.fixture.scenarios.RecreateParties;
import domainapp.integtests.tests.PolyAppIntegTest;

import java.util.List;
import javax.inject.Inject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommunicationChannelsIntegTest extends PolyAppIntegTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    CommunicationChannelsContributions communicationChannelsContributions;
    @Inject
    CommunicationChannelsMenu communicationChannelsMenu;

    public static class Create extends CommunicationChannelsIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            RecreateParties fs = new RecreateParties();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            final Party party = fs.getParties().get(0);

            // when
            wrap(communicationChannelsContributions).createCommunicationChannel(party, "0207 123 4567");

            // then
            final List<CommunicationChannel> all = wrap(communicationChannelsMenu).listAll();
            assertThat(all.size(), is(1));
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            final RecreateParties fs = new RecreateParties();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            final Party party = fs.getParties().get(0);

            wrap(communicationChannelsContributions).createCommunicationChannel(party, "0207 123 4567");
            nextTransaction();

            // then expect
            expectedException.expect(DisabledException.class);
            expectedException.expectMessage("Already owns a communication channel");

            // when
            wrap(communicationChannelsContributions).createCommunicationChannel(party, "0207 123 4567");
            nextTransaction();
        }

        @Test
        public void whenFailsValidation() throws Exception {

            // given
            final RecreateParties fs = new RecreateParties();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            final Party party = fs.getParties().get(0);

            // then expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("No exclamation marks allowed in details");

            // when
            wrap(communicationChannelsContributions).createCommunicationChannel(party, "&?!*!$");
        }
    }

    public static class Delete extends CommunicationChannelsIntegTest {

        @Test
        public void whenExists() throws Exception {

            // given
            RecreateParties fs = new RecreateParties();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            final Party party = fs.getParties().get(0);

            wrap(communicationChannelsContributions).createCommunicationChannel(party, "0207 123 4567");
            nextTransaction();

            assertThat(wrap(communicationChannelsMenu).listAll().size(), is(1));

            // when
            wrap(communicationChannelsContributions).deleteCommunicationChannel(party);
            nextTransaction();

            // then
            assertThat(wrap(communicationChannelsMenu).listAll().size(), is(0));
        }

        @Test
        public void whenDoesNotExist() throws Exception {

            // given
            final RecreateParties fs = new RecreateParties();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            final Party party = fs.getParties().get(0);

            // then expect
            expectedException.expect(DisabledException.class);
            expectedException.expectMessage("Does not own a communication channel");

            // when
            wrap(communicationChannelsContributions).deleteCommunicationChannel(party);
        }
    }

}