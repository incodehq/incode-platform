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
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_updateDescription;
import org.incode.module.commchannel.dom.impl.emailaddress.CommunicationChannelOwner_newEmailAddress;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_updateDescription_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;

    @Inject
    CommunicationChannelOwner_newEmailAddress communicationChannelOwner_newEmailAddress;

    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    @Inject
    CommunicationChannel_updateDescription communicationChannel_updateDescription;


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
            CommunicationChannel_updateDescription_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();
            final String newDescription = fakeDataService.lorem().sentence();

            wrap(communicationChannel_updateDescription).updateDescription(communicationChannel, newDescription);

            assertThat(communicationChannel.getDescription()).isEqualTo(newDescription);
        }
    }

    public static class DefaultIntegrationTest extends CommunicationChannel_updateDescription_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();

            final String descr = communicationChannel_updateDescription.default1UpdateDescription(communicationChannel);

            assertThat(descr).isEqualTo(communicationChannel.getDescription());
        }

    }

    public static class RaisesEventIntegrationTest extends CommunicationChannel_updateDescription_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannel_updateDescription.UpdateDescriptionEvent ev;

            @Subscribe
            public void on(CommunicationChannel_updateDescription.UpdateDescriptionEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            final String newParagraph = fakeDataService.lorem().paragraph();
            wrap(communicationChannel_updateDescription).updateDescription(channel, newParagraph);
            
            assertThat(testSubscriber.ev.getArguments().get(1)).isEqualTo(newParagraph);
        }
    }
}