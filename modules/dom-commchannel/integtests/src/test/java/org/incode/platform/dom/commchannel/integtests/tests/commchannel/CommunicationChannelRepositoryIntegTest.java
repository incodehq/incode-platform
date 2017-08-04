package org.incode.platform.dom.commchannel.integtests.tests.commchannel;

import java.util.SortedSet;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelRepository;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelRepositoryIntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject fredDemoOwner;
    DemoObject billDemoOwner;

    @Inject
    CommunicationChannelRepository communicationChannelRepository;

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