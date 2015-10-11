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

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelOwner_communicationChannels;
import org.incode.module.commchannel.dom.impl.phoneorfax.CommunicationChannelOwner_newPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber_update;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneOrFaxNumber_update_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelOwner_newPhoneOrFaxNumber communicationChannelOwner_newPhoneOrFaxNumber;
    @Inject
    CommunicationChannelOwner_communicationChannels communicationChannelOwner_communicationChannels;

    CommChannelDemoObject fredDemoOwner;
    PhoneOrFaxNumber fredPhone;

    @Inject
    PhoneOrFaxNumber_update phoneOrFaxNumber_update;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        wrap(communicationChannelOwner_newPhoneOrFaxNumber)
                .newPhoneOrFaxNumber(fredDemoOwner, CommunicationChannelType.PHONE_NUMBER, "0207 999 8888", "Home",
                        "Fred Smith's home phone");
        fredPhone = (PhoneOrFaxNumber)wrap(communicationChannelOwner_communicationChannels)
                                            .communicationChannels(fredDemoOwner).first();
    }

    public static class ActionImplementationIntegrationTest extends
            PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void happy_case() throws Exception {

            final PhoneOrFaxNumber returned = wrap(phoneOrFaxNumber_update)
                    .updatePhoneOrFaxNumber(fredPhone, CommunicationChannelType.FAX_NUMBER, "0207 111 2222");

            assertThat(fredPhone.getPhoneNumber()).isEqualTo("0207 111 2222");
            assertThat(fredPhone.getType()).isEqualTo(CommunicationChannelType.FAX_NUMBER);
            
            assertThat(returned).isSameAs(fredPhone);
        }
    }

    public static class ChoicesIntegrationTest extends PhoneOrFaxNumber_update_IntegTest {
        @Test
        public void fax_and_phone_are_the_only_valid_choices() throws Exception {

            final List<CommunicationChannelType> types = phoneOrFaxNumber_update.choices1UpdatePhoneOrFaxNumber();

            assertThat(types).hasSize(2);
            assertThat(types).contains(CommunicationChannelType.FAX_NUMBER);
            assertThat(types).contains(CommunicationChannelType.PHONE_NUMBER);
        }
    }

    public static class Default1IntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void should_default_to_current_type() throws Exception {
            final CommunicationChannelType defaultType = phoneOrFaxNumber_update.default1UpdatePhoneOrFaxNumber(
                    fredPhone);

            assertThat(defaultType).isEqualTo(fredPhone.getType());
        }

    }

    public static class Default2IntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void should_default_to_current_number() throws Exception {
            final String defaultNumber = phoneOrFaxNumber_update.default2UpdatePhoneOrFaxNumber(
                    fredPhone);

            assertThat(defaultNumber).isEqualTo(fredPhone.getPhoneNumber());
        }

    }

}