package org.incode.example.commchannel.dom.impl.channel;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

@Mixin
public class CommunicationChannel_owner {

    //region > injected services
    @Inject
    CommunicationChannelOwnerLinkRepository communicationChannelOwnerLinkRepository;
    //endregion

    //region > constructor, mixedIn accessor
    private final CommunicationChannel<?> communicationChannel;
    public CommunicationChannel_owner(final CommunicationChannel<?> communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    @Programmatic
    public CommunicationChannel<?> getCommunicationChannel() {
        return communicationChannel;
    }
    //endregion

    //region > $$
    public static class DomainEvent extends CommunicationChannel.PropertyDomainEvent
                                        <CommunicationChannel_owner,Object> { }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @Property(
            domainEvent = DomainEvent.class,
            notPersisted = true
    )
    @PropertyLayout(hidden = Where.PARENTED_TABLES)
    public Object $$() {
        final CommunicationChannelOwnerLink link = communicationChannelOwnerLinkRepository.getOwnerLink(communicationChannel);
        return link != null? link.getOwner(): null;
    }
    //endregion

    //region > setOwner
    @Programmatic
    public void setOwner(final Object owner) {
        communicationChannelOwnerLinkRepository.removeOwnerLink(this.communicationChannel);
        communicationChannelOwnerLinkRepository.createLink(this.communicationChannel, owner);
    }
    //endregion

}
