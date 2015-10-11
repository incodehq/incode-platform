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
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelOwner_communicationChannels;
import org.incode.module.commchannel.dom.impl.emailaddress.CommunicationChannelOwner_newEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.CommunicationChannelOwner_newPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.CommunicationChannelOwner_newPostalAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelOwner_communicationChannels_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;
    CommChannelDemoObject billDemoOwner;

    @Inject
    CommunicationChannelOwner_newEmailAddress communicationChannelOwnerNewEmailAddress;

    @Inject
    CommunicationChannelOwner_newPostalAddress postalAddressOwnerNewPostalAddress;

    @Inject
    CommunicationChannelOwner_newPhoneOrFaxNumber communicationChannelOwnerNewPhoneOrFaxNumber;

    @Inject
    CommunicationChannelOwner_communicationChannels communicationChannelOwner_communicationChannels;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        billDemoOwner = wrap(commChannelDemoObjectMenu).create("Bill");

        wrap(communicationChannelOwnerNewEmailAddress)
                .newEmailAddress(fredDemoOwner, "fred@gmail.com", "Home", "Fred Smith's home email");
        wrap(communicationChannelOwnerNewEmailAddress)
                .newEmailAddress(fredDemoOwner, "fred.smith@somecompany.com", "Work", "Fred Smith's work email");

        wrap(communicationChannelOwnerNewPhoneOrFaxNumber)
                .newPhoneOrFaxNumber(fredDemoOwner, CommunicationChannelType.PHONE_NUMBER, "0208 111 1111", "Home Phone", "Fred Smith's home phone number");
        wrap(communicationChannelOwnerNewPhoneOrFaxNumber)
                .newPhoneOrFaxNumber(fredDemoOwner, CommunicationChannelType.PHONE_NUMBER, "0207 222 2222", "Work Phone", "Fred Smith's work phone number");
        wrap(communicationChannelOwnerNewPhoneOrFaxNumber)
                .newPhoneOrFaxNumber(fredDemoOwner, CommunicationChannelType.FAX_NUMBER, "0207 222 3333", "Work Fax", "Fred Smith's work fax number");
        wrap(postalAddressOwnerNewPostalAddress)
                .newPostalAddress(fredDemoOwner, "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK", "Home", "Fred Smith's home", false);
        wrap(postalAddressOwnerNewPostalAddress)
                .newPostalAddress(fredDemoOwner, "Grange School", "Wavertree", null, "Liverpool", "L36 1QQ", "UK", "Work", "Fred Smith's work", false);

        wrap(communicationChannelOwnerNewEmailAddress)
                .newEmailAddress(billDemoOwner, "bill@yahoo.com", "Home", "Bill Jones' home email");
        wrap(communicationChannelOwnerNewPhoneOrFaxNumber)
                .newPhoneOrFaxNumber(billDemoOwner, CommunicationChannelType.PHONE_NUMBER, "01865 222 222", "Work Phone", "Bill Jones' work phone number");
        wrap(communicationChannelOwnerNewPhoneOrFaxNumber)
                .newPhoneOrFaxNumber(billDemoOwner, CommunicationChannelType.FAX_NUMBER, "01865 222 333", "Work Fax", "Bill Jones' work fax number");
        wrap(postalAddressOwnerNewPostalAddress)
                .newPostalAddress(billDemoOwner, "Beatles Museum", "Albert Dock", null, "Liverpool", "L5 1AB", "UK", "Work", "Bill Jones's work", false);

    }

    public static class ActionImplementationIntegrationTest extends
            CommunicationChannelOwner_communicationChannels_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final SortedSet<CommunicationChannel> channels = wrap(
                    communicationChannelOwner_communicationChannels).communicationChannels(fredDemoOwner);

            assertThat(channels).hasSize(7);
        }

    }

}