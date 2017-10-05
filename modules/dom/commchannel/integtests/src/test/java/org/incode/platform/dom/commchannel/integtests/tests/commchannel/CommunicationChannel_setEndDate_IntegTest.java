package org.incode.platform.dom.commchannel.integtests.tests.commchannel;

import java.util.SortedSet;

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
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelRepository;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_setEndDate_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    DemoObject fredDemoOwner;
    SortedSet<CommunicationChannel> fredChannels;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Foo");

        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home Email", "Fred Smith's home email", new LocalDate(2017, 1, 1), null);

        fredChannels = communicationChannelRepository.findByOwner(fredDemoOwner);
    }


    public static class ActionImplementationIntegrationTest extends CommunicationChannel_setEndDate_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();
            final LocalDate endDate = communicationChannel.getStartDate().plusMonths(3);

            wrap(communicationChannel).setEndDate(endDate);

            assertThat(communicationChannel.getEndDate()).isEqualTo(endDate);
        }

    }

    public static class RaisesEventIntegrationTest extends CommunicationChannel_setEndDate_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannel.EndDateDomainEvent ev;

            @Subscribe
            public void on(CommunicationChannel.EndDateDomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            final LocalDate endDate = channel.getStartDate().plusMonths(3);
            wrap(channel).setEndDate(endDate);

            assertThat(testSubscriber.ev).isNotNull();
        }
    }

}
