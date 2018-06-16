package org.incode.domainapp.extended.integtests.examples.commchannel.integtests.phoneorfaxnumber;

import java.util.List;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber;
import org.incode.example.commchannel.dom.impl.phoneorfax.PhoneOrFaxNumber_update;
import org.incode.example.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.domainapp.extended.integtests.examples.commchannel.CommChannelModuleIntegTestAbstract;

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