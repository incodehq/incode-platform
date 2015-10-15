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
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress_update;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailAddress_update_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;
    EmailAddress fredEmail;

    EmailAddress_update mixinUpdate(final EmailAddress emailAddress) {
        return mixin(EmailAddress_update.class, emailAddress);
    }

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        wrap(mixinNewEmailAddress(fredDemoOwner)).__(
                "fred@gmail.com", "Home", "Fred Smith's home email");

        final SortedSet<CommunicationChannel> communicationChannels = wrap(mixinCommunicationChannels(fredDemoOwner)).__();
        fredEmail = (EmailAddress)communicationChannels.first();
    }


    public static class ActionImplementationIntegrationTest extends
            EmailAddress_update_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final EmailAddress returned = wrap(mixinUpdate(fredEmail)).__("frederick@yahoo.com");

            assertThat(wrap(fredEmail).getEmailAddress()).isEqualTo("frederick@yahoo.com");
            assertThat(returned).isSameAs(fredEmail);
        }
    }

    public static class DefaultIntegrationTest extends EmailAddress_update_IntegTest {

        @Test
        public void should_default_to_current_email_address() throws Exception {
            final String defaultEmail = mixinUpdate(fredEmail).default0__();

            assertThat(defaultEmail).isEqualTo(fredEmail.getEmailAddress());
        }
    }


    public static class RaisesEventIntegrationTest extends EmailAddress_update_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            EmailAddress_update.Event ev;

            @Subscribe
            public void on(EmailAddress_update.Event ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            final String newAddress = "frederick@yahoo.com";
            wrap(mixinUpdate(fredEmail)).__(newAddress);

            assertThat(testSubscriber.ev.getSource().getEmailAddress()).isSameAs(fredEmail);
            assertThat(testSubscriber.ev.getArguments().get(0)).isEqualTo(newAddress);
        }
    }

}