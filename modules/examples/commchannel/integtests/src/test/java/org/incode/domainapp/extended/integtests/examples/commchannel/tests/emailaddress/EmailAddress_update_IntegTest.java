package org.incode.domainapp.extended.integtests.examples.commchannel.tests.emailaddress;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.example.alias.demo.examples.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.emailaddress.EmailAddress;
import org.incode.example.commchannel.dom.impl.emailaddress.EmailAddress_update;
import org.incode.domainapp.extended.integtests.examples.commchannel.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailAddress_update_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject fredDemoOwner;
    EmailAddress fredEmail;

    EmailAddress_update mixinUpdate(final EmailAddress emailAddress) {
        return mixin(EmailAddress_update.class, emailAddress);
    }

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Fred");
        wrap(mixinNewEmailAddress(fredDemoOwner)).$$(
                "fred@gmail.com", "Home", "Fred Smith's home email");

        final SortedSet<CommunicationChannel> communicationChannels = wrap(mixinCommunicationChannels(fredDemoOwner)).$$();
        fredEmail = (EmailAddress)communicationChannels.first();
    }


    public static class ActionImplementationIntegrationTest extends
            EmailAddress_update_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final EmailAddress returned = wrap(mixinUpdate(fredEmail)).$$("frederick@yahoo.com");

            assertThat(wrap(fredEmail).getEmailAddress()).isEqualTo("frederick@yahoo.com");
            assertThat(returned).isSameAs(fredEmail);
        }
    }

    public static class DefaultIntegrationTest extends EmailAddress_update_IntegTest {

        @Test
        public void should_default_to_current_email_address() throws Exception {
            final String defaultEmail = mixinUpdate(fredEmail).default0$$();

            assertThat(defaultEmail).isEqualTo(fredEmail.getEmailAddress());
        }
    }


    public static class RaisesEventIntegrationTest extends EmailAddress_update_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            EmailAddress_update.DomainEvent ev;

            @Subscribe
            public void on(EmailAddress_update.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {

            final String newAddress = "frederick@yahoo.com";
            wrap(mixinUpdate(fredEmail)).$$(newAddress);

            assertThat(testSubscriber.ev.getSource().getEmailAddress()).isSameAs(fredEmail);
            assertThat(testSubscriber.ev.getArguments().get(0)).isEqualTo(newAddress);
        }
    }

}