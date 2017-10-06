package org.incode.platform.dom.commchannel.integtests.tests.phoneorfaxnumber;

import java.util.List;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber_update;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneOrFaxNumber_update_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject fredDemoOwner;
    PhoneOrFaxNumber fredPhone;

    PhoneOrFaxNumber_update mixinUpdate(final PhoneOrFaxNumber phoneOrFaxNumber) {
        return mixin(PhoneOrFaxNumber_update.class, phoneOrFaxNumber);
    }

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Fred");
        wrap(mixinNewPhoneOrFaxNumber(fredDemoOwner))
                .$$(CommunicationChannelType.PHONE_NUMBER, "0207 999 8888", "Home",
                        "Fred Smith's home phone", new LocalDate(2017, 1, 1), null);
        fredPhone = (PhoneOrFaxNumber) wrap(mixinCommunicationChannels(fredDemoOwner))
                .$$().first();
    }

    public static class ActionImplementationIntegrationTest extends
            PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void update_phone_number() throws Exception {

            final PhoneOrFaxNumber returned =
                    wrap(mixinUpdate(fredPhone)).$$(CommunicationChannelType.FAX_NUMBER, "0207 111 2222", fredPhone.getStartDate(), null);

            assertThat(fredPhone.getPhoneNumber()).isEqualTo("0207 111 2222");
            assertThat(fredPhone.getType()).isEqualTo(CommunicationChannelType.FAX_NUMBER);

            assertThat(returned).isSameAs(fredPhone);
        }

        @Test
        public void update_end_date() throws Exception {

            final PhoneOrFaxNumber returned =
                    wrap(mixinUpdate(fredPhone)).$$(CommunicationChannelType.FAX_NUMBER, fredPhone.getPhoneNumber(), fredPhone.getStartDate(), fredPhone.getStartDate().plusMonths(3));

            assertThat(fredPhone.getEndDate()).isEqualTo(fredPhone.getStartDate().plusMonths(3));
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

    public static class Default0IntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void should_default_to_current_type() throws Exception {
            final CommunicationChannelType defaultType = mixinUpdate(fredPhone).default0$$(
            );

            assertThat(defaultType).isEqualTo(fredPhone.getType());
        }

    }

    public static class Default1IntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void should_default_to_current_number() throws Exception {
            final String defaultNumber = mixinUpdate(fredPhone).default1$$();

            assertThat(defaultNumber).isEqualTo(fredPhone.getPhoneNumber());
        }
    }

    public static class Default2IntegrationTest extends PhoneOrFaxNumber_update_IntegTest {

        @Test
        public void should_default_to_current_start_date() throws Exception {
            final LocalDate defaultStartDate = mixinUpdate(fredPhone).default2$$();

            assertThat(defaultStartDate).isEqualTo(fredPhone.getStartDate());
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
            wrap(mixinUpdate(fredPhone)).$$(CommunicationChannelType.FAX_NUMBER, newPhoneNumber, new LocalDate(2017, 1, 1), null);

            assertThat(testSubscriber.ev.getSource().getPhoneOrFaxNumber()).isSameAs(fredPhone);
            assertThat(testSubscriber.ev.getArguments().get(0)).isEqualTo(CommunicationChannelType.FAX_NUMBER);
            assertThat(testSubscriber.ev.getArguments().get(1)).isEqualTo(newPhoneNumber);
            assertThat(testSubscriber.ev.getArguments().get(2)).isEqualTo(new LocalDate(2017, 1, 1));
            assertThat(testSubscriber.ev.getArguments().get(3)).isNull();
        }
    }

}