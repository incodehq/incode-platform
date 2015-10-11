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

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelOwner_communicationChannels;
import org.incode.module.commchannel.dom.impl.emailaddress.CommunicationChannelOwner_newEmailAddress;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress_update;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailAddress_update_IntegTest extends CommChannelModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    @Inject
    CommunicationChannelOwner_newEmailAddress communicationChannelOwner_newEmailAddress;
    @Inject
    CommunicationChannelOwner_communicationChannels communicationChannelOwner_communicationChannels;

    CommChannelDemoObject fredDemoOwner;
    EmailAddress fredEmail;

    @Inject
    EmailAddress_update emailAddress_update;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        wrap(communicationChannelOwner_newEmailAddress)
                .newEmailAddress(fredDemoOwner, "fred@gmail.com", "Home", "Fred Smith's home email");

        final SortedSet<CommunicationChannel> communicationChannels = wrap(
                communicationChannelOwner_communicationChannels).communicationChannels(fredDemoOwner);
        fredEmail = (EmailAddress)communicationChannels.first();
    }

    public static class ActionImplementationIntegrationTest extends
            EmailAddress_update_IntegTest {

        @Test
        public void happy_case() throws Exception {
            wrap(emailAddress_update).updateEmailAddress(fredEmail, "frederick@yahoo.com");

            assertThat(wrap(fredEmail).getEmailAddress()).isEqualTo("frederick@yahoo.com");
        }
    }

    public static class DefaultIntegrationTest extends EmailAddress_update_IntegTest {

        @Test
        public void should_default_to_current_email_address() throws Exception {
            final String defaultEmail = emailAddress_update.default1UpdateEmailAddress(fredEmail);

            assertThat(defaultEmail).isEqualTo(fredEmail.getEmailAddress());
        }
    }

}