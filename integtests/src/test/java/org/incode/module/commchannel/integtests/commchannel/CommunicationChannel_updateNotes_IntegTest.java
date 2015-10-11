/*
 *  Copyright 2014~2015 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package org.incode.module.commchannel.integtests.commchannel;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelRepository;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_updateNotes;
import org.incode.module.commchannel.dom.impl.emailaddress.CommunicationChannelOwner_newEmailAddress;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_updateNotes_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;

    @Inject
    CommunicationChannelOwner_newEmailAddress communicationChannelOwner_newEmailAddress;

    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    @Inject
    CommunicationChannel_updateNotes communicationChannel_updateNotes;


    SortedSet<CommunicationChannel> fredChannels;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Foo");

        wrap(communicationChannelOwner_newEmailAddress)
                .newEmailAddress(fredDemoOwner, "fred@gmail.com", "Home", "Fred Smith's home email");

        fredChannels = communicationChannelRepository.findByOwner(fredDemoOwner);
    }

    public static class ActionImplementationIntegrationTest extends
            CommunicationChannel_updateNotes_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();
            final String newNotes = fakeDataService.lorem().paragraph();

            wrap(communicationChannel_updateNotes).updateNotes(communicationChannel, newNotes);

            assertThat(communicationChannel.getNotes()).isEqualTo(newNotes);
        }
    }

    public static class DefaultIntegrationTest extends CommunicationChannel_updateNotes_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();

            final String notes = communicationChannel_updateNotes.default1UpdateNotes(communicationChannel);

            assertThat(notes).isEqualTo(communicationChannel.getNotes());
        }

    }

    public static class RaisesEventIntegrationTest extends CommunicationChannel_updateNotes_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannel_updateNotes.UpdateNotesEvent ev;

            @Subscribe
            public void on(CommunicationChannel_updateNotes.UpdateNotesEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            final String newParagraph = fakeDataService.lorem().paragraph();
            wrap(communicationChannel_updateNotes).updateNotes(channel, newParagraph);

            assertThat(testSubscriber.ev.getArguments().get(1)).isEqualTo(newParagraph);
        }
    }

}