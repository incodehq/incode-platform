package org.incode.example.commchannel.dom.impl.channel;

import java.util.SortedSet;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.example.commchannel.dom.CommChannelModule;

public abstract class T_communicationChannels<T> {

    //region > constructor
    private final T communicationChannelOwner;
    public T_communicationChannels(final T communicationChannelOwner) {
        this.communicationChannelOwner = communicationChannelOwner;
    }
    //endregion

    //region > $$
    public static class DomainEvent extends CommChannelModule.CollectionDomainEvent
                                        <T_communicationChannels, CommunicationChannel> { }
    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(
            named = "Communication Channels", // regression in isis 1.11.x requires this to be specified
            defaultView = "table"
    )
    @Collection(
            domainEvent = DomainEvent.class
    )
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public SortedSet<CommunicationChannel> $$() {
        return communicationChannelRepository.findByOwner(communicationChannelOwner);
    }

    //endregion

    //region > injected services
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    //endregion


}
