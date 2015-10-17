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

import java.util.Collection;
import java.util.Objects;
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
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_updatePurpose;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_updatePurpose_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    CommChannelDemoObject fredDemoOwner;
    SortedSet<CommunicationChannel> fredChannels;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Foo");

        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home Email", "Fred Smith's home email");

        fredChannels = communicationChannelRepository.findByOwner(fredDemoOwner);
    }


    public static class ActionImplementationIntegrationTest extends
            CommunicationChannel_updatePurpose_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();

            final CommunicationChannel_updatePurpose mixinUpdatePurpose = mixinUpdatePurpose(communicationChannel);

            final Collection<String> choices = mixinUpdatePurpose.choices0$$();
            final String newPurpose = fakeDataService.collections().anyOfExcept(
                    choices, s -> Objects.equals(s, communicationChannel.getPurpose()) );

            wrap(mixinUpdatePurpose).$$(newPurpose);

            assertThat(communicationChannel.getPurpose()).isEqualTo(newPurpose);
        }

    }

    public static class DefaultIntegrationTest extends CommunicationChannel_updatePurpose_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();

            final String descr = mixinUpdatePurpose(communicationChannel).default0$$();

            assertThat(descr).isEqualTo(communicationChannel.getPurpose());
        }

    }

    public static class RaisesEventIntegrationTest extends CommunicationChannel_updatePurpose_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannel_updatePurpose.DomainEvent ev;

            @Subscribe
            public void on(CommunicationChannel_updatePurpose.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel channel = fredChannels.first();

            final CommunicationChannel_updatePurpose mixinUpdatePurpose = mixinUpdatePurpose(channel);
            final String newPurpose = fakeDataService.collections().anyOf(mixinUpdatePurpose.choices0$$().toArray(new String[]{}));

            wrap(mixinUpdatePurpose).$$(newPurpose);

            assertThat(testSubscriber.ev.getSource().getCommunicationChannel()).isSameAs(channel);
            assertThat(testSubscriber.ev.getArguments().get(0)).isEqualTo(newPurpose);
        }
    }


}

