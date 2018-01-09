package org.incode.domainapp.example.dom.lib.poly.dom.poly.ccowner;

import java.util.List;
import javax.annotation.PostConstruct;
import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.incode.domainapp.example.dom.lib.poly.dom.democommchannel.CommunicationChannel;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CommunicationChannelOwnerLink.class
)
public class CommunicationChannelOwnerLinks {

    //region > init
    PolymorphicAssociationLink.Factory<CommunicationChannel,CommunicationChannelOwner,CommunicationChannelOwnerLink,CommunicationChannelOwnerLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        CommunicationChannel.class,
                        CommunicationChannelOwner.class,
                        CommunicationChannelOwnerLink.class,
                        CommunicationChannelOwnerLink.InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByCommunicationChannel (programmatic)
    @Programmatic
    public CommunicationChannelOwnerLink findByCommunicationChannel(final CommunicationChannel communicationChannel) {
        return container.firstMatch(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByCommunicationChannel",
                        "communicationChannel", communicationChannel));
    }
    //endregion

    //region > findByOwner (programmatic)
    @Programmatic
    public List<CommunicationChannelOwnerLink> findByOwner(final CommunicationChannelOwner owner) {
        if(owner == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(owner);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByOwner",
                        "ownerObjectType", bookmark.getObjectType(),
                        "ownerIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > createLink (programmatic)
    @Programmatic
    public void createLink(final CommunicationChannel communicationChannel, final CommunicationChannelOwner owner) {
        linkFactory.createLink(communicationChannel, owner);
    }
    //endregion


    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    //endregion

}
