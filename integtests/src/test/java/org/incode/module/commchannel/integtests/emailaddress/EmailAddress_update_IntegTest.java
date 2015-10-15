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

import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        wrap(newEmailAddress(fredDemoOwner)).__(
                "fred@gmail.com", "Home", "Fred Smith's home email");

        final SortedSet<CommunicationChannel> communicationChannels = wrap(communicationChannels(fredDemoOwner)).__();
        fredEmail = (EmailAddress)communicationChannels.first();
    }

    EmailAddress_update updateEmailAddress(final EmailAddress emailAddress) {
        return mixin(EmailAddress_update.class, emailAddress);
    }


    public static class ActionImplementationIntegrationTest extends
            EmailAddress_update_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final EmailAddress returned = wrap(updateEmailAddress(fredEmail)).__("frederick@yahoo.com");

            assertThat(wrap(fredEmail).getEmailAddress()).isEqualTo("frederick@yahoo.com");
            assertThat(returned).isSameAs(fredEmail);
        }
    }

    public static class DefaultIntegrationTest extends EmailAddress_update_IntegTest {

        @Test
        public void should_default_to_current_email_address() throws Exception {
            final String defaultEmail = updateEmailAddress(fredEmail).default0__();

            assertThat(defaultEmail).isEqualTo(fredEmail.getEmailAddress());
        }
    }

}