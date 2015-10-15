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
package org.incode.module.commchannel.integtests.emailaddress;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.emailaddress.CommunicationChannelOwner_newEmailAddress;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelOwner_newEmailAddress_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
    }

    public static class ActionImplementationIntegrationTest extends
            CommunicationChannelOwner_newEmailAddress_IntegTest {

        @Test
        public void happyCase() throws Exception {
            // given
            final SortedSet<CommunicationChannel> channelsBefore = wrap(communicationChannels(fredDemoOwner)).__();
            assertThat(channelsBefore).hasSize(0);

            // when
            wrap(newEmailAddress(fredDemoOwner)).__("fred@gmail.com", "Home", "Fred Smith's home email");

            // then
            final SortedSet<CommunicationChannel> channelsAfter = wrap(communicationChannels(fredDemoOwner)).__();

            assertThat(channelsAfter).hasSize(1);
            final CommunicationChannel communicationChannel = channelsAfter.first();

            assertThat(communicationChannel.getName()).isEqualTo("fred@gmail.com");
            assertThat(communicationChannel.getDescription()).isEqualTo("Home");
            assertThat(communicationChannel.getNotes()).isEqualTo("Fred Smith's home email");
            assertThat(communicationChannel.getType()).isEqualTo(CommunicationChannelType.EMAIL_ADDRESS);
            assertThat(communicationChannel.getLocation()).isNull();
            assertThat(communicationChannel.getId()).isNotNull();

            assertThat(communicationChannel).isInstanceOf(EmailAddress.class);
            final EmailAddress emailAddress = (EmailAddress)communicationChannel;
            assertThat(emailAddress.getEmailAddress()).isEqualTo("fred@gmail.com");
        }
    }


    public static class RaisesEventIntegrationTest extends CommunicationChannelOwner_newEmailAddress_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannelOwner_newEmailAddress.Event ev;

            @Subscribe
            public void on(CommunicationChannelOwner_newEmailAddress.Event ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            wrap(newEmailAddress(fredDemoOwner)).__("fred@gmail.com", "Home", "Fred Smith's home email");

            assertThat(testSubscriber.ev).isNotNull();
        }

    }

}