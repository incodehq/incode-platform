package org.incode.module.commchannel.dom.impl.channel;

import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLink;
import org.incode.module.commchannel.dom.impl.ownerlink.CommunicationChannelOwnerLinkRepository;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CommunicationChannel.class
)
public class CommunicationChannelRepository {

    public String getId() {
        return "incodeCommChannel.CommunicationChannelRepository";
    }

    //region > injected services
    @Inject
    CommunicationChannelOwnerLinkRepository linkRepository;
    @Inject
    DomainObjectContainer container;
    //endregion

    @Programmatic
    public SortedSet<CommunicationChannel> findByOwner(final Object owner) {
        final List<CommunicationChannelOwnerLink> links = linkRepository.findByOwner(owner);
        return Sets.newTreeSet(
                Iterables.transform(links, CommunicationChannelOwnerLink.Functions.communicationChannel()));
    }

    @Programmatic
    public SortedSet<CommunicationChannel> findByOwnerAndType(
            final Object owner,
            final CommunicationChannelType type) {
        final List<CommunicationChannelOwnerLink> links =
                linkRepository.findByOwnerAndCommunicationChannelType(owner, type);
        return Sets.newTreeSet(Iterables.transform(
                links, CommunicationChannelOwnerLink.Functions.communicationChannel()));
    }

    @Programmatic
    public SortedSet<CommunicationChannel> findOtherByOwnerAndType(
            final Object owner,
            final CommunicationChannelType type,
            final CommunicationChannel exclude) {
        final SortedSet<CommunicationChannel> communicationChannels = findByOwnerAndType(owner, type);
        communicationChannels.remove(exclude);
        return communicationChannels;
    }


}
