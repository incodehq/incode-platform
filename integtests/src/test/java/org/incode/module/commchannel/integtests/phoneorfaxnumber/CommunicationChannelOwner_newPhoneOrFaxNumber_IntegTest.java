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
package org.incode.module.commchannel.integtests.phoneorfaxnumber;

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelOwner_communicationChannels;
import org.incode.module.commchannel.dom.impl.phoneorfax.CommunicationChannelOwner_newPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelOwner_newPhoneOrFaxNumber_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelOwner_communicationChannels communicationChannelOwner_communicationChannels;

    CommChannelDemoObject fredDemoOwner;

    @Inject
    CommunicationChannelOwner_newPhoneOrFaxNumber communicationChannelOwner_newPhoneOrFaxNumber;


    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
    }

    public static class ActionImplementationIntegrationTest extends
            CommunicationChannelOwner_newPhoneOrFaxNumber_IntegTest {

        @Test
        public void can_create_phone_number() throws Exception {

            // given
            final SortedSet<CommunicationChannel> communicationChannelsBefore = wrap(
                    communicationChannelOwner_communicationChannels).communicationChannels(fredDemoOwner);
            assertThat(communicationChannelsBefore).hasSize(0);

            // when
            wrap(communicationChannelOwner_newPhoneOrFaxNumber)
                    .newPhoneOrFaxNumber(fredDemoOwner, CommunicationChannelType.PHONE_NUMBER, "0207 999 8888", "Work", "Fred's work number");

            // then
            final SortedSet<CommunicationChannel> communicationChannelsAfter = wrap(
                    communicationChannelOwner_communicationChannels).communicationChannels(fredDemoOwner);

            assertThat(communicationChannelsAfter).hasSize(1);
            final CommunicationChannel communicationChannel = communicationChannelsAfter.first();

            assertThat(communicationChannel.getName()).isEqualTo("0207 999 8888");
            assertThat(communicationChannel.getDescription()).isEqualTo("Work");
            assertThat(communicationChannel.getNotes()).isEqualTo("Fred's work number");
            assertThat(communicationChannel.getType()).isEqualTo(CommunicationChannelType.PHONE_NUMBER);
            assertThat(communicationChannel.getLocation()).isNull();
            assertThat(communicationChannel.getId()).isNotNull();

            assertThat(communicationChannel).isInstanceOf(PhoneOrFaxNumber.class);
            final PhoneOrFaxNumber phoneOrFaxNumber = (PhoneOrFaxNumber)communicationChannel;
            assertThat(phoneOrFaxNumber.getPhoneNumber()).isEqualTo("0207 999 8888");
        }
    }

    public static class ValidateIntegrationTest extends CommunicationChannelOwner_newPhoneOrFaxNumber_IntegTest {

        @Test
        public void attempt_to_create_with_invalid_type() throws Exception {

            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("");

            wrap(communicationChannelOwner_newPhoneOrFaxNumber).newPhoneOrFaxNumber(
                    fredDemoOwner,
                    CommunicationChannelType.EMAIL_ADDRESS,
                    "0207 111 2222",
                    "Fred's home phone or fax",
                    "... but attempted to create using wrong comm channel type");
        }
    }

    public static class ChoicesIntegrationTest extends CommunicationChannelOwner_newPhoneOrFaxNumber_IntegTest {

        @Test
        public void fax_and_phone_are_the_only_valid_choices() throws Exception {

            final List<CommunicationChannelType> types = communicationChannelOwner_newPhoneOrFaxNumber
                    .choices1NewPhoneOrFaxNumber();

            assertThat(types).hasSize(2);
            assertThat(types).contains(CommunicationChannelType.FAX_NUMBER);
            assertThat(types).contains(CommunicationChannelType.PHONE_NUMBER);
        }
    }

    public static class DefaultIntegrationTest extends CommunicationChannelOwner_newPhoneOrFaxNumber_IntegTest {

        @Test
        public void phone_is_the_default_choice() throws Exception {

            final CommunicationChannelType type = communicationChannelOwner_newPhoneOrFaxNumber
                    .default1NewPhoneOrFaxNumber();

            assertThat(type).isEqualTo(CommunicationChannelType.PHONE_NUMBER);
        }

    }

    public static class RaisesEventIntegrationTest extends CommunicationChannelOwner_newPhoneOrFaxNumber_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannelOwner_newPhoneOrFaxNumber.NewPhoneOrFaxEvent ev;

            @Subscribe
            public void on(CommunicationChannelOwner_newPhoneOrFaxNumber.NewPhoneOrFaxEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            wrap(communicationChannelOwner_newPhoneOrFaxNumber).newPhoneOrFaxNumber(fredDemoOwner,
                    CommunicationChannelType.PHONE_NUMBER, "0207 999 8888", "Work", "Fred's work number");

            assertThat(testSubscriber.ev).isNotNull();
        }

    }

}