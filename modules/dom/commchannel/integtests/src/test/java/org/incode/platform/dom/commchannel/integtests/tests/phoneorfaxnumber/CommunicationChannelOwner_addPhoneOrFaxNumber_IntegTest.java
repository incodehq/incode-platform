package org.incode.platform.dom.commchannel.integtests.tests.phoneorfaxnumber;

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannelOwner_addPhoneOrFaxNumber_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject fredDemoOwner;


    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");
    }

    public static class ActionImplementationIntegrationTest extends
            CommunicationChannelOwner_addPhoneOrFaxNumber_IntegTest {

        @Test
        public void can_create_phone_number() throws Exception {

            // given
            final SortedSet<CommunicationChannel> communicationChannelsBefore =
                    wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
            assertThat(communicationChannelsBefore).hasSize(0);

            // when
            wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                    CommunicationChannelType.PHONE_NUMBER, "0207 999 8888", "Work", "Fred's work number");

            // then
            final SortedSet<CommunicationChannel> communicationChannelsAfter =
                    wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
            assertThat(communicationChannelsAfter).hasSize(1);

            final CommunicationChannel communicationChannel = communicationChannelsAfter.first();
            assertThat(communicationChannel.getName()).isEqualTo("0207 999 8888");
            assertThat(communicationChannel.getPurpose()).isEqualTo("Work");
            assertThat(communicationChannel.getNotes()).isEqualTo("Fred's work number");
            assertThat(communicationChannel.getType()).isEqualTo(CommunicationChannelType.PHONE_NUMBER);
            assertThat(communicationChannel.getLocation()).isNull();
            assertThat(communicationChannel.getId()).isNotNull();

            assertThat(communicationChannel).isInstanceOf(PhoneOrFaxNumber.class);
            final PhoneOrFaxNumber phoneOrFaxNumber = (PhoneOrFaxNumber)communicationChannel;
            assertThat(phoneOrFaxNumber.getPhoneNumber()).isEqualTo("0207 999 8888");
        }
    }

    public static class ValidateIntegrationTest extends CommunicationChannelOwner_addPhoneOrFaxNumber_IntegTest {

        @Test
        public void attempt_to_create_with_invalid_type() throws Exception {

            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("");

            wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                    CommunicationChannelType.EMAIL_ADDRESS,
                    "0207 111 2222",
                    "Fred's home phone or fax",
                    "... but attempted to create using wrong comm channel type");
        }
    }

    public static class ChoicesIntegrationTest extends CommunicationChannelOwner_addPhoneOrFaxNumber_IntegTest {

        @Test
        public void fax_and_phone_are_the_only_valid_choices() throws Exception {

            final List<CommunicationChannelType> types = mixinNewPhoneOrFaxNumber(fredDemoOwner).choices0$$();

            assertThat(types).hasSize(2);
            assertThat(types).contains(CommunicationChannelType.FAX_NUMBER);
            assertThat(types).contains(CommunicationChannelType.PHONE_NUMBER);
        }
    }

    public static class DefaultIntegrationTest extends CommunicationChannelOwner_addPhoneOrFaxNumber_IntegTest {

        @Test
        public void phone_is_the_default_choice() throws Exception {

            final CommunicationChannelType type = mixinNewPhoneOrFaxNumber(fredDemoOwner).default0$$();

            assertThat(type).isEqualTo(CommunicationChannelType.PHONE_NUMBER);
        }

    }

    public static class RaisesEventIntegrationTest extends CommunicationChannelOwner_addPhoneOrFaxNumber_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            T_addPhoneOrFaxNumber.DomainEvent ev;

            @Subscribe
            public void on(T_addPhoneOrFaxNumber.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner)).$$(
                    CommunicationChannelType.PHONE_NUMBER, "0207 999 8888", "Work", "Fred's work number");

            assertThat(testSubscriber.ev).isNotNull();
            assertThat(testSubscriber.ev.getSource().getCommunicationChannelOwner()).isSameAs(fredDemoOwner);
        }

    }

}