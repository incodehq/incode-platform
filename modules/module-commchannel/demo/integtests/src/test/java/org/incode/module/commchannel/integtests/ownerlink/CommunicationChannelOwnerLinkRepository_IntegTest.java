package org.incode.module.commchannel.integtests.ownerlink;

import javax.inject.Inject;

import org.junit.Before;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import domainapp.modules.exampledom.module.commchannel.dom.demo.CommChannelDemoObject;
import domainapp.modules.exampledom.module.commchannel.dom.demo.CommChannelDemoObjectMenu;
import domainapp.modules.exampledom.module.commchannel.fixture.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommunicationChannelOwnerLinkRepository_IntegTest extends CommChannelModuleIntegTestAbstract {

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

        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home Email", "Fred Smith's home email");
        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred.smith@somecompany.com", "Work Email",
                "Fred Smith's work email");

        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                CommunicationChannelType.PHONE_NUMBER, "0208 111 1111", "Home Phone", "Fred Smith's home phone number");
        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                CommunicationChannelType.PHONE_NUMBER, "0207 222 2222", "Mobile Phone",
                "Fred Smith's work phone number");
        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                CommunicationChannelType.FAX_NUMBER, "0207 222 3333", "Work Fax", "Fred Smith's work fax number");
        wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                "Flat 2a", "45 Penny Lane", "Allerton", "Liverpool", "L39 5AA", "UK",
                "Shipping Address", "Fred Smith's home", false);
        wrap(mixinNewPostalAddress(fredDemoOwner)).$$(
                "Grange School", "Wavertree", null, "Liverpool", "L36 1QQ", "UK",
                "Billing Address", "Fred Smith's work", false);

        wrap(mixinNewEmailAddress(billDemoOwner)).$$("bill@yahoo.com", "Home Email", "Bill Jones' home email");
        wrap(mixinNewPhoneOrFaxNumber(billDemoOwner)).$$(
                CommunicationChannelType.PHONE_NUMBER, "01865 222 222", "Work Number", "Bill Jones' work phone number");
        wrap(mixinNewPhoneOrFaxNumber(billDemoOwner)).$$(
                CommunicationChannelType.FAX_NUMBER, "01865 222 333", "Work Fax", "Bill Jones' work fax number");
        wrap(mixinNewPostalAddress(billDemoOwner)).$$(
                "Beatles Museum", "Albert Dock", null, "Liverpool", "L5 1AB", "UK",
                "Shipping Address", "Bill Jones's work", false);

    }

    public static class FindByCommunicationChannelIntegrationTest extends
            CommunicationChannelOwnerLinkRepository_IntegTest {
        // TODO

    }

    public static class FindByOwnerIntegrationTest extends CommunicationChannelOwnerLinkRepository_IntegTest {
        // TODO

    }

    public static class FindByOwnerAndCommunicationChannelTypeIntegrationTest
            extends CommunicationChannelOwnerLinkRepository_IntegTest {
        // TODO

    }

}