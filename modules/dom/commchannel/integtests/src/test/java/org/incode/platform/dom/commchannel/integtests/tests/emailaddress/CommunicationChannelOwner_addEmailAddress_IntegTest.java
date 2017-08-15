package org.incode.platform.dom.commchannel.integtests.tests.emailaddress;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelOwner_addEmailAddress_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject fredDemoOwner;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
    }

    public static class ActionImplementationIntegrationTest extends
            CommunicationChannelOwner_addEmailAddress_IntegTest {

        @Test
        public void happyCase() throws Exception {
            // given
            final SortedSet<CommunicationChannel> channelsBefore = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
            assertThat(channelsBefore).hasSize(0);

            // when
            wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home", "Fred Smith's home email");

            // then
            final SortedSet<CommunicationChannel> channelsAfter = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();

            assertThat(channelsAfter).hasSize(1);
            final CommunicationChannel communicationChannel = channelsAfter.first();

            assertThat(communicationChannel.getName()).isEqualTo("fred@gmail.com");
            assertThat(communicationChannel.getPurpose()).isEqualTo("Home");
            assertThat(communicationChannel.getNotes()).isEqualTo("Fred Smith's home email");
            assertThat(communicationChannel.getType()).isEqualTo(CommunicationChannelType.EMAIL_ADDRESS);
            assertThat(communicationChannel.getLocation()).isNull();
            assertThat(communicationChannel.getId()).isNotNull();

            assertThat(communicationChannel).isInstanceOf(EmailAddress.class);
            final EmailAddress emailAddress = (EmailAddress)communicationChannel;
            assertThat(emailAddress.getEmailAddress()).isEqualTo("fred@gmail.com");
        }
    }


    public static class RaisesEventIntegrationTest extends CommunicationChannelOwner_addEmailAddress_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            T_addEmailAddress.DomainEvent ev;

            @Subscribe
            public void on(T_addEmailAddress.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home", "Fred Smith's home email");

            assertThat(testSubscriber.ev).isNotNull();
            assertThat(testSubscriber.ev.getSource().getCommunicationChannelOwner()).isSameAs(fredDemoOwner);
        }

    }

}