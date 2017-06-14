package org.incode.module.commchannel.dom.impl.channel;

import java.util.SortedSet;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;

/**
 * Removes the {@link CommunicationChannel} from its owner, optionally specifying a replacement.
 */
@Mixin
public class CommunicationChannel_remove1 {

    //region > mixins
    private Object mixinOwner() {
        return factoryService.mixin(CommunicationChannel_owner.class, communicationChannel).$$();
    }
    //endregion

    //region > constructor
    private final CommunicationChannel<?> communicationChannel;
    public CommunicationChannel_remove1(final CommunicationChannel<?> communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
    @Programmatic
    public CommunicationChannel<?> getCommunicationChannel() {
        return communicationChannel;
    }

    //endregion

    //region > $$

    public static class DomainEvent extends CommunicationChannel.ActionDomainEvent<CommunicationChannel_remove1> {
        public CommunicationChannel<?> getReplacement() {
            return (CommunicationChannel<?>) getArguments().get(0);
        }
    }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            cssClass = "btn-warning",
            cssClassFa = "trash",
            named = "Remove"
    )
    public Object $$(
            @ParameterLayout(named = "Replace with")
            @Nullable
            final CommunicationChannel replacement) {
        final Object owner = mixinOwner();
        removeLink();
        return owner;
    }

    public boolean hide$$() {
        return choices0$$().isEmpty();
    }

    public SortedSet<CommunicationChannel> choices0$$() {
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
