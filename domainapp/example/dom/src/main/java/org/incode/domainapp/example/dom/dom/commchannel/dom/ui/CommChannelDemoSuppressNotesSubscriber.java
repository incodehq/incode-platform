package org.incode.domainapp.example.dom.dom.commchannel.dom.ui;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommChannelDemoSuppressNotesSubscriber extends AbstractSubscriber {
    @Subscribe
    public void on(CommunicationChannel.NotesDomainEvent ev) {
        switch (ev.getEventPhase()) {
        case HIDE:
            // uncomment as an example of how to influence the UI
            // (the notes property should disappear)
            // ev.hide();
        }
    }
}
