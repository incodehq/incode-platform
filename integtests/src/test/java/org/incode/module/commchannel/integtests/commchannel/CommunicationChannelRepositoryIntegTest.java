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
import org.incode.module.commchannel.dom.impl.emailaddress.CommunicationChannelOwner_newEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.CommunicationChannelOwner_newPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.CommunicationChannelOwner_newPostalAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelRepositoryIntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;
    CommChannelDemoObject billDemoOwner;

    @Inject
    CommunicationChannelOwner_newEmailAddress communicationChannelOwner_newEmailAddress;

    @Inject
    CommunicationChannelOwner_newPostalAddress communicationChannelOwner_newPostalAddress;

    @Inject
    CommunicationChannelOwner_newPhoneOrFaxNumber communicationChannelOwner_newPhoneOrFaxNumber;

    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        billDemoOwner = wrap(commChannelDemoObjectMenu).create("Bill");

        wrap(communicationChannelOwner_newEmailAddress)
                .__("fred@gmail.com", "Home", "Fred Smith's home email");
        wrap(communicationChannelOwner_newEmailAddress)
                .__("fred.smith@somecompany.com", "Work", "Fred Smith's work email");

        wrap(communicationChannelOwner_newPhoneOrFaxNumber)
                .__(CommunicationChannelType.PHONE_NUMBER, "0208 111 1111", "Home Phone",
                        "Fred Smith's home phone number");
        wrap(communicationChannelOwner_newPhoneOrFaxNumber)
                .__(CommunicationChannelType.PHONE_NUMBER, "0207 222 2222", "Work Phone",
                        "Fred Smith's work phone number");
        wrap(communicationChannelOwner_newPhoneOrFaxNumber)
                .__(CommunicationChannelType.FAX_NUMBER, "0207 222 3333", "Work Fax", "Fred Smith's work fax number");
        wrap(communicationChannelOwner_newPostalAddress)
                .__("Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK", "Home", "Fred Smith's home",
                        false);
        wrap(communicationChannelOwner_newPostalAddress)
                .__("Grange School", "Wavertree", null, "Liverpool", "L36 1QQ", "UK", "Work", "Fred Smith's work",
                        false);

        wrap(communicationChannelOwner_newEmailAddress)
                .__("bill@yahoo.com", "Home", "Bill Jones' home email");
        wrap(communicationChannelOwner_newPhoneOrFaxNumber)
                .__(CommunicationChannelType.PHONE_NUMBER, "01865 222 222", "Work Phone",
                        "Bill Jones' work phone number");
        wrap(communicationChannelOwner_newPhoneOrFaxNumber)
                .__(CommunicationChannelType.FAX_NUMBER, "01865 222 333", "Work Fax", "Bill Jones' work fax number");
        wrap(communicationChannelOwner_newPostalAddress)
                .__("Beatles Museum", "Albert Dock", null, "Liverpool", "L5 1AB", "UK", "Work", "Bill Jones's work",
                        false);

    }

    public static class FindByOwnerIntegrationTest extends CommunicationChannelRepositoryIntegTest {

        @Test
        public void happy_case() throws Exception {
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository.findByOwner(fredDemoOwner);
            assertThat(channels.size()).isEqualTo(7);
        }
    }

    public static class FindByOwnerAndTypeIntegrationTest extends CommunicationChannelRepositoryIntegTest {

        @Test
        public void email() throws Exception {
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository.findByOwnerAndType(fredDemoOwner, CommunicationChannelType.EMAIL_ADDRESS);
            assertThat(channels.size()).isEqualTo(2);
        }

        @Test
        public void post() throws Exception {
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository.findByOwnerAndType(fredDemoOwner, CommunicationChannelType.POSTAL_ADDRESS);
            assertThat(channels.size()).isEqualTo(2);
        }

        @Test
        public void fax() throws Exception {
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository.findByOwnerAndType(fredDemoOwner, CommunicationChannelType.FAX_NUMBER);
            assertThat(channels.size()).isEqualTo(1);
        }

        @Test
        public void phone() throws Exception {
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository.findByOwnerAndType(
                    fredDemoOwner, CommunicationChannelType.PHONE_NUMBER);
            assertThat(channels.size()).isEqualTo(2);
        }

    }

    public static class FindOtherByOwnerAndTypeIntegrationTest extends CommunicationChannelRepositoryIntegTest {


        @Test
        public void email() throws Exception {
            // given
            final CommunicationChannel channel = communicationChannelRepository.findByOwnerAndType(fredDemoOwner, CommunicationChannelType.EMAIL_ADDRESS).first();

            // when
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository.findOtherByOwnerAndType(
                    fredDemoOwner, CommunicationChannelType.EMAIL_ADDRESS, channel);

            // then
            assertThat(channels.size()).isEqualTo(1);
        }

        @Test
        public void post() throws Exception {
            // given
            final CommunicationChannel channel = communicationChannelRepository.findByOwnerAndType(fredDemoOwner, CommunicationChannelType.POSTAL_ADDRESS).first();
            // when
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository
                    .findOtherByOwnerAndType(fredDemoOwner, CommunicationChannelType.POSTAL_ADDRESS, channel);
            // then
            assertThat(channels.size()).isEqualTo(1);
        }

        @Test
        public void fax() throws Exception {
            // given
            final CommunicationChannel channel = communicationChannelRepository.findByOwnerAndType(fredDemoOwner, CommunicationChannelType.FAX_NUMBER).first();
            // when
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository
                    .findOtherByOwnerAndType(fredDemoOwner, CommunicationChannelType.FAX_NUMBER, channel);
            // then
            assertThat(channels.size()).isEqualTo(0);
        }

        @Test
        public void phone() throws Exception {
            // given
            final CommunicationChannel channel = communicationChannelRepository.findByOwnerAndType(fredDemoOwner, CommunicationChannelType.PHONE_NUMBER).first();
            // when
            final SortedSet<CommunicationChannel> channels = communicationChannelRepository
                    .findOtherByOwnerAndType(fredDemoOwner, CommunicationChannelType.PHONE_NUMBER, channel);
            // then
            assertThat(channels.size()).isEqualTo(1);
        }

    }

}