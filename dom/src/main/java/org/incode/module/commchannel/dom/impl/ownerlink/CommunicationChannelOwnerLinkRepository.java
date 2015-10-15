/*
 *  Copyright 2015 incode.org
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.commchannel.dom.impl.ownerlink;

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

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.api.owner.CommunicationChannelOwner;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CommunicationChannelOwnerLink.class
)
public class CommunicationChannelOwnerLinkRepository {

    //region > injected services
    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    BookmarkService bookmarkService;
    //endregion

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

    @Programmatic
    public CommunicationChannelOwnerLink findByCommunicationChannel(final CommunicationChannel communicationChannel) {
        return container.firstMatch(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByCommunicationChannel",
                        "communicationChannel", communicationChannel));
    }

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

    @Programmatic
    public List<CommunicationChannelOwnerLink> findByOwnerAndCommunicationChannelType(
            final CommunicationChannelOwner owner,
            final CommunicationChannelType communicationChannelType) {
        if(owner == null) {
            return null;
        }
        if(communicationChannelType == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(owner);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByOwnerAndCommunicationChannelType",
                        "ownerObjectType", bookmark.getObjectType(),
                        "ownerIdentifier", bookmark.getIdentifier(),
                        "communicationChannelType", communicationChannelType));
    }

    @Programmatic
    public CommunicationChannelOwnerLink createLink(final CommunicationChannel communicationChannel, final CommunicationChannelOwner owner) {
        final CommunicationChannelOwnerLink link = linkFactory.createLink(communicationChannel, owner);
        // copy over the type, to support subsequent querying.
        link.setCommunicationChannelType(communicationChannel.getType());
        return link;
    }


    @Programmatic
    public CommunicationChannelOwnerLink getOwnerLink(
            final CommunicationChannel communicationChannel) {
        return findByCommunicationChannel(communicationChannel);
    }

    @Programmatic
    public void removeOwnerLink(final CommunicationChannel communicationChannel) {
        final CommunicationChannelOwnerLink ownerLink = getOwnerLink(communicationChannel);
        if(ownerLink != null) {
            container.remove(ownerLink);
        }
    }

}
