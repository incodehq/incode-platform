package org.incode.platform.dom.commchannel.integtests.tests.commchannel;

import java.util.List;
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
import org.incode.domainapp.example.dom.dom.commchannel.fixture.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelRepository;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_remove1;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_remove_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    @Inject
    CommunicationChannelOwnerLinkRepository communicationChannelOwnerLinkRepository;

    DemoObject fredDemoOwner;

    SortedSet<CommunicationChannel> fredChannels;
    List<CommunicationChannelOwnerLink> fredLinks;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).create("Fred");

        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home Email", "Fred Smith's home email");
        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred.smith@somecompany.com", "Work Email",
                "Fred Smith's work email");
        fredChannels = communicationChannelRepository.findByOwner(fredDemoOwner);
        assertThat(fredChannels).hasSize(2);

        fredLinks = communicationChannelOwnerLinkRepository.findByOwner(fredDemoOwner);
        assertThat(fredLinks).hasSize(2);
    }


    public static class ActionImplementationIntegrationTest extends CommunicationChannel_remove_IntegTest {

        @Test
        public void when_no_replacement() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            mixin(CommunicationChannel_remove1.class, channel).$$(null);

            assertThat(communicationChannelRepository.findByOwner(fredDemoOwner)).hasSize(1);
            assertThat(communicationChannelOwnerLinkRepository.findByOwner(fredDemoOwner)).hasSize(1);
        }

        @Test
        public void when_replacement() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            final CommunicationChannel channel2 = fredChannels.last();
            mixin(CommunicationChannel_remove1.class, channel).$$(channel2);

            assertThat(communicationChannelRepository.findByOwner(fredDemoOwner)).hasSize(1);
            assertThat(communicationChannelOwnerLinkRepository.findByOwner(fredDemoOwner)).hasSize(1);
        }
    }

    public static class ChoicesIntegrationTest extends CommunicationChannel_remove_IntegTest {

        @Test
        public void when_replacement() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            final CommunicationChannel channel2 = fredChannels.last();

            final SortedSet<CommunicationChannel> choices = mixinRemove(channel)
                    .choices0$$();

            assertThat(choices).hasSize(1);
            assertThat(choices.first()).isSameAs(channel2);
        }
    }

    public static class RaisesEventIntegrationTest extends CommunicationChannel_remove_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannel_remove1.DomainEvent ev;

            @Subscribe
            public void on(CommunicationChannel_remove1.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void when_no_replacement() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            wrap(mixinRemove(channel)).$$(null);

            assertThat(testSubscriber.ev).isNotNull();
            assertThat(testSubscriber.ev.getSource().getCommunicationChannel()).isSameAs(channel);
        }

        @Test
        public void when_replacement() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            final CommunicationChannel channel2 = fredChannels.last();
            wrap(mixinRemove(channel)).$$(channel2);

            assertThat(testSubscriber.ev).isNotNull();
            assertThat(testSubscriber.ev.getSource().getCommunicationChannel()).isSameAs(channel);
            assertThat(testSubscriber.ev.getReplacement()).isSameAs(channel2);
        }
    }

}