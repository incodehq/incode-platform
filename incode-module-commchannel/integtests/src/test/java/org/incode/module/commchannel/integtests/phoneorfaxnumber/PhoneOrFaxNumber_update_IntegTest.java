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

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

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

    CommChannelDemoObject fredDemoOwner;
    PhoneOrFaxNumber fredPhone;

    PhoneOrFaxNumber_update mixinUpdate(final PhoneOrFaxNumber phoneOrFaxNumber) {
        return container.mixin(PhoneOrFaxNumber_update.class, phoneOrFaxNumber);
    }

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner))
                .$$(CommunicationChannelType.PHONE_NUMBER, "0207 999 8888", "Home",
                        "Fred Smith's home phone");
        fredPhone = (PhoneOrFaxNumber)wrap(mixinCommunicationChannels(fredDemoOwner))
                                            .$$().first();
    }

    public static class ActionImplementationIntegrationTest extends
            PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void happy_case() throws Exception {

            final PhoneOrFaxNumber returned =
                    wrap(mixinUpdate(fredPhone)).$$(CommunicationChannelType.FAX_NUMBER, "0207 111 2222");

            assertThat(fredPhone.getPhoneNumber()).isEqualTo("0207 111 2222");
            assertThat(fredPhone.getType()).isEqualTo(CommunicationChannelType.FAX_NUMBER);
            
            assertThat(returned).isSameAs(fredPhone);
        }
    }

    public static class ChoicesIntegrationTest extends PhoneOrFaxNumber_update_IntegTest {
        @Test
        public void fax_and_phone_are_the_only_valid_choices() throws Exception {

            final List<CommunicationChannelType> types = mixinUpdate(fredPhone).choices1$$();

            assertThat(types).hasSize(2);
            assertThat(types).contains(CommunicationChannelType.FAX_NUMBER);
            assertThat(types).contains(CommunicationChannelType.PHONE_NUMBER);
        }
    }

    public static class Default1IntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void should_default_to_current_type() throws Exception {
            final CommunicationChannelType defaultType = mixinUpdate(fredPhone).default0$$(
            );

            assertThat(defaultType).isEqualTo(fredPhone.getType());
        }

    }

    public static class Default2IntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void should_default_to_current_number() throws Exception {
            final String defaultNumber = mixinUpdate(fredPhone).default1$$();

            assertThat(defaultNumber).isEqualTo(fredPhone.getPhoneNumber());
        }
    }


    public static class RaisesEventIntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            PhoneOrFaxNumber_update.DomainEvent ev;

            @Subscribe
            public void on(PhoneOrFaxNumber_update.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            final String newPhoneNumber = "0207 111 2222";
            wrap(mixinUpdate(fredPhone)).$$(CommunicationChannelType.FAX_NUMBER, newPhoneNumber);

            assertThat(testSubscriber.ev.getSource().getPhoneOrFaxNumber()).isSameAs(fredPhone);
            assertThat(testSubscriber.ev.getArguments().get(0)).isEqualTo(CommunicationChannelType.FAX_NUMBER);
            assertThat(testSubscriber.ev.getArguments().get(1)).isEqualTo(newPhoneNumber);
        }
    }


}