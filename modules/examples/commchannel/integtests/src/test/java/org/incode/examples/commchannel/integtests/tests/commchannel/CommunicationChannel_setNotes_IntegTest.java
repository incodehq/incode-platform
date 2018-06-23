package org.incode.examples.commchannel.integtests.tests.commchannel;

import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.examples.commchannel.demo.usage.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObject;
import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObjectMenu;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.example.commchannel.dom.impl.channel.CommunicationChannelRepository;
import org.incode.examples.commchannel.integtests.CommChannelModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunicationChannel_setNotes_IntegTest extends CommChannelModuleIntegTestAbstract {

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

        wrap(mixinNewEmailAddress(fredDemoOwner)).$$("fred@gmail.com", "Home Email", "Fred Smith's home email");

        fredChannels = communicationChannelRepository.findByOwner(fredDemoOwner);
    }


    public static class EditIntegrationTest extends CommunicationChannel_setNotes_IntegTest {

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel communicationChannel = fredChannels.first();
            final String newNotes = fakeDataService.lorem().paragraph();

            wrap(communicationChannel).setNotes(newNotes);

            assertThat(communicationChannel.getNotes()).isEqualTo(newNotes);
        }
    }


    public static class RaisesEventIntegrationTest extends CommunicationChannel_setNotes_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class TestSubscriber extends AbstractSubscriber {
            CommunicationChannel.NotesDomainEvent ev;

            @Subscribe
            public void on(CommunicationChannel.NotesDomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        TestSubscriber testSubscriber;

        @Test
        public void happy_case() throws Exception {
            final CommunicationChannel channel = fredChannels.first();
            final String newParagraph = fakeDataService.lorem().paragraph();
            wrap(channel).setNotes(newParagraph);

            assertThat(testSubscriber.ev.getSource()).isSameAs(channel);
            assertThat(testSubscriber.ev.getNewValue()).isEqualTo(newParagraph);
        }
    }

}