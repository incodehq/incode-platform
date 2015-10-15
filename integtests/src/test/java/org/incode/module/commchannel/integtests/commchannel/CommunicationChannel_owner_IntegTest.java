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

import org.junit.Before;
import org.junit.Test;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelRepository;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_owner;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_owner_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    CommChannelDemoObject fredDemoOwner;
    CommChannelDemoObject billDemoOwner;

    SortedSet<CommunicationChannel> fredChannels;
    SortedSet<CommunicationChannel> billChannels;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        wrap(newEmailAddress(fredDemoOwner))
                .__("fred@gmail.com", "Home", "Fred Smith's home email");
        wrap(newEmailAddress(fredDemoOwner))
                .__("fred.smith@somecompany.com", "Work", "Fred Smith's work email");
        fredChannels = communicationChannelRepository.findByOwner(fredDemoOwner);
        assertThat(fredChannels).hasSize(2);

        billDemoOwner = wrap(commChannelDemoObjectMenu).create("Bill");
        wrap(newEmailAddress(billDemoOwner))
                .__("bill@yahoo.com", "Home", "Bill Jones' home email");
        billChannels = communicationChannelRepository.findByOwner(billDemoOwner);
        assertThat(billChannels).hasSize(1);
    }

    public static class ActionImplementationIntegrationTest extends CommunicationChannel_owner_IntegTest {

        @Test
        public void happy_case() throws Exception {
            for (final CommunicationChannel channel : fredChannels) {
                final CommunicationChannel_owner owner = mixin(CommunicationChannel_owner.class, channel);
                assertThat(owner.__()).isSameAs(fredDemoOwner);
            }
        }
    }



}