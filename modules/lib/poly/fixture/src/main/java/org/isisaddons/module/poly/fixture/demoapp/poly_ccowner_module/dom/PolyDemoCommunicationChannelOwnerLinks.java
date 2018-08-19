package org.isisaddons.module.poly.fixture.demoapp.poly_ccowner_module.dom;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.commchannel.PolyDemoCommunicationChannel;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolyDemoCommunicationChannelOwnerLink.class
)
public class PolyDemoCommunicationChannelOwnerLinks {

    //region > init
    PolymorphicAssociationLink.Factory<PolyDemoCommunicationChannel,PolyDemoCommunicationChannelOwner,PolyDemoCommunicationChannelOwnerLink,PolyDemoCommunicationChannelOwnerLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        PolyDemoCommunicationChannel.class,
                        PolyDemoCommunicationChannelOwner.class,
                        PolyDemoCommunicationChannelOwnerLink.class,
                        PolyDemoCommunicationChannelOwnerLink.InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByCommunicationChannel (programmatic)
    @Programmatic
    public PolyDemoCommunicationChannelOwnerLink findByCommunicationChannel(final PolyDemoCommunicationChannel communicationChannel) {
        return container.firstMatch(
                new QueryDefault<>(PolyDemoCommunicationChannelOwnerLink.class,
                        "findByCommunicationChannel",
                        "communicationChannel", communicationChannel));
    }
    //endregion

    //region > findByOwner (programmatic)
    @Programmatic
    public List<PolyDemoCommunicationChannelOwnerLink> findByOwner(final PolyDemoCommunicationChannelOwner owner) {
        if(owner == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(owner);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(PolyDemoCommunicationChannelOwnerLink.class,
                        "findByOwner",
                        "ownerObjectType", bookmark.getObjectType(),
                        "ownerIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > createLink (programmatic)
    @Programmatic
    public void createLink(final PolyDemoCommunicationChannel communicationChannel, final PolyDemoCommunicationChannelOwner owner) {
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
