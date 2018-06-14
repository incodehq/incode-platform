package org.incode.example.dom.commchannel.integtests.tests.commchannel;

import java.util.SortedSet;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannelRepository;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel_owner;
import org.incode.example.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_owner_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;

    DemoObject fredDemoOwner;
    DemoObject billDemoOwner;

    SortedSet<CommunicationChannel> fredChannels;
    SortedSet<CommunicationChannel> billChannels;

    CommunicationChannel_owner mixinOwner(final CommunicationChannel channel) {
        return mixin(CommunicationChannel_owner.class, channel);
    }

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        fredDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Fred");
        wrap(mixinNewEmailAddress(fredDemoOwner))
                .$$("fred@gmail.com", "Home Email", "Fred Smith's home email");
        wrap(mixinNewEmailAddress(fredDemoOwner))
                .$$("fred.smith@somecompany.com", "Work Email", "Fred Smith's work email");
        fredChannels = communicationChannelRepository.findByOwner(fredDemoOwner);
        assertThat(fredChannels).hasSize(2);

        billDemoOwner = wrap(commChannelDemoObjectMenu).createDemoObject("Bill");
        wrap(mixinNewEmailAddress(billDemoOwner))
                .$$("bill@yahoo.com", "Home Email", "Bill Jones' home email");
        billChannels = communicationChannelRepository.findByOwner(billDemoOwner);
        assertThat(billChannels).hasSize(1);
    }

    public static class ActionImplementationIntegrationTest extends CommunicationChannel_owner_IntegTest {

        @Test
        public void happy_case() throws Exception {
            for (final CommunicationChannel channel : fredChannels) {
                final CommunicationChannel_owner owner = mixinOwner(channel);
                assertThat(owner.$$()).isSameAs(fredDemoOwner);
            }
        }

    }



}