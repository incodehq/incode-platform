package org.incode.example.commchannel.dom.impl.channel;

import java.util.SortedSet;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.example.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

/**
 * Removes the {@link CommunicationChannel} from its owner, with no replacement.
 */
@Mixin
public class CommunicationChannel_remove0 {

    //region > mixins
    private Object mixinOwner() {
        return factoryService.mixin(CommunicationChannel_owner.class, communicationChannel).$$();
    }
    //endregion

    //region > constructor
    private final CommunicationChannel<?> communicationChannel;
    public CommunicationChannel_remove0(final CommunicationChannel<?> communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    @Programmatic
    public CommunicationChannel<?> getCommunicationChannel() {
        return communicationChannel;
    }

    //endregion

    //region > $$

    public static class DomainEvent extends CommunicationChannel.ActionDomainEvent<CommunicationChannel_remove0> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            cssClass = "btn-warning",
            cssClassFa = "trash",
            named = "Remove"
    )
    public Object $$() {
        final Object owner = mixinOwner();
        removeLink();
        return owner;
    }

    public boolean hide$$() {
        return !others().isEmpty();
    }

    private SortedSet<CommunicationChannel> others() {
        return communicationChannelRepository.findOtherByOwnerAndType(
                mixinOwner(), this.communicationChannel.getType(),
                this.communicationChannel);
    }

    void removeLink() {
        ownerLinkRepository.removeOwnerLink(communicationChannel);
        repositoryService.remove(communicationChannel);
    }
    //endregion


    //region > injected services
    @Inject
    CommunicationChannelOwnerLinkRepository ownerLinkRepository;
    @Inject
    CommunicationChannelRepository communicationChannelRepository;
    @Inject
    RepositoryService repositoryService;
    @Inject
    FactoryService factoryService;
    //endregion

}
