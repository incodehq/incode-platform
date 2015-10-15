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
package org.incode.module.commchannel.integtests.ownerlink;

import javax.inject.Inject;

import org.junit.Before;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

public class CommunicationChannelOwnerLinkRepositoryIntegTest extends CommChannelModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject fredDemoOwner;
    CommChannelDemoObject billDemoOwner;

    @Inject
    CommunicationChannelOwnerLinkRepository communicationChannelOwnerLinkRepository;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
        billDemoOwner = wrap(commChannelDemoObjectMenu).create("Bill");

        wrap(newEmailAddress(fredDemoOwner)).__("fred@gmail.com", "Home", "Fred Smith's home email");
        wrap(newEmailAddress(fredDemoOwner)).__("fred.smith@somecompany.com", "Work", "Fred Smith's work email");

        wrap(newPhoneOrFaxNumber(fredDemoOwner)).__(
                CommunicationChannelType.PHONE_NUMBER, "0208 111 1111", "Home Phone", "Fred Smith's home phone number");
        wrap(newPhoneOrFaxNumber(fredDemoOwner)).__(
                CommunicationChannelType.PHONE_NUMBER, "0207 222 2222", "Work Phone", "Fred Smith's work phone number");
        wrap(newPhoneOrFaxNumber(fredDemoOwner)).__(
                CommunicationChannelType.FAX_NUMBER, "0207 222 3333", "Work Fax", "Fred Smith's work fax number");
        wrap(newPostalAddress(fredDemoOwner)).__(
                "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK", "Home", "Fred Smith's home",
                false);
        wrap(newPostalAddress(fredDemoOwner)).__(
                "Grange School", "Wavertree", null, "Liverpool", "L36 1QQ", "UK", "Work", "Fred Smith's work",
                false);

        wrap(newEmailAddress(billDemoOwner)).__("bill@yahoo.com", "Home", "Bill Jones' home email");
        wrap(newPhoneOrFaxNumber(billDemoOwner)).__(
                CommunicationChannelType.PHONE_NUMBER, "01865 222 222", "Work Phone", "Bill Jones' work phone number");
        wrap(newPhoneOrFaxNumber(billDemoOwner)).__(
                CommunicationChannelType.FAX_NUMBER, "01865 222 333", "Work Fax", "Bill Jones' work fax number");
        wrap(newPostalAddress(billDemoOwner)).__(
                "Beatles Museum", "Albert Dock", null, "Liverpool", "L5 1AB", "UK", "Work", "Bill Jones's work",
                false);

    }

    public static class FindByCommunicationChannelIntegrationTest extends CommunicationChannelOwnerLinkRepositoryIntegTest {

    }

    public static class FindByOwnerIntegrationTest extends CommunicationChannelOwnerLinkRepositoryIntegTest {

    }

    public static class FindByOwnerAndCommunicationChannelTypeIntegrationTest
            extends CommunicationChannelOwnerLinkRepositoryIntegTest {

    }

}