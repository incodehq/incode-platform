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

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = CommunicationChannelOwnerLink.class
)
public class CommunicationChannelOwnerLinkRepository {

    //region > findByCommunicationChannel (programmatic)
    @Programmatic
    public CommunicationChannelOwnerLink findByCommunicationChannel(final CommunicationChannel communicationChannel) {
        return repositoryService.firstMatch(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByCommunicationChannel",
                        "communicationChannel", communicationChannel));
    }
    //endregion

    //region > findByOwner (programmatic)
    @Programmatic
    public List<CommunicationChannelOwnerLink> findByOwner(final Object owner) {
        if(owner == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(owner);
        if(bookmark == null) {
            return null;
        }
        final String ownerStr = bookmark.toString();
        return repositoryService.allMatches(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByOwner",
                        "ownerStr", ownerStr));
    }
    //endregion

    //region > findByOwnerAndCommunicationChannelType (programmatic)
    @Programmatic
    public List<CommunicationChannelOwnerLink> findByOwnerAndCommunicationChannelType(
            final Object owner,
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
        final String ownerStr = bookmark.toString();
        return repositoryService.allMatches(
                new QueryDefault<>(CommunicationChannelOwnerLink.class,
                        "findByOwnerAndCommunicationChannelType",
                        "ownerStr", ownerStr,
                        "communicationChannelType", communicationChannelType));
    }
    //endregion

    //region > create (programmatic)
    @Programmatic
    public CommunicationChannelOwnerLink createLink(
            final CommunicationChannel communicationChannel,
            final Object owner) {

        final CommunicationChannelType type = communicationChannel.getType();
        final Class<? extends CommunicationChannelOwnerLink> subtype = subtypeClassFor(owner, type);

        final CommunicationChannelOwnerLink link = repositoryService.instantiate(subtype);

        link.setCommunicationChannel(communicationChannel);
        // copy over the type, to support subsequent querying.
        link.setCommunicationChannelType(type);

        final Bookmark bookmark = bookmarkService.bookmarkFor(owner);
        link.setOwner(owner);
        link.setOwnerStr(bookmark.toString());

        repositoryService.persist(link);

        return link;
    }

    private Class<? extends CommunicationChannelOwnerLink> subtypeClassFor(
            final Object classified,
            CommunicationChannelType type) {
        Class<?> domainClass = classified.getClass();
        for (SubtypeProvider subtypeProvider : subtypeProviders) {
            Class<? extends CommunicationChannelOwnerLink> subtype = subtypeProvider.subtypeFor(domainClass, type);
            if(subtype != null) {
                return subtype;
            }
        }
        throw new IllegalStateException(String.format(
                "No subtype of CommunicationChannelOwnerLink was found for '%s' and type '%s'; implement the CommunicationChannelOwnerLinkRepository.SubtypeProvider SPI",
                domainClass.getName(), type));
    }
    //endregion


    @Programmatic
    public CommunicationChannelOwnerLink getOwnerLink(
            final CommunicationChannel communicationChannel) {
        return findByCommunicationChannel(communicationChannel);
    }

    @Programmatic
    public void removeOwnerLink(final CommunicationChannel communicationChannel) {
        final CommunicationChannelOwnerLink ownerLink = getOwnerLink(communicationChannel);
        if(ownerLink != null) {
            repositoryService.remove(ownerLink);
        }
    }

    //region > SubtypeProvider SPI

    /**
     * SPI to be implemented (as a {@link DomainService}) for any domain object to which {@link CommunicationChannelOwnerLink}s can be
     * attached.
     */
    public interface SubtypeProvider {
        /**
         * @return the subtype of {@link CommunicationChannelOwnerLink} to use to hold the (type-safe) communicationChannelOwnerLink of the domain object with respect to the provided {@link CommunicationChannelType}.
         */
        @Programmatic
        Class<? extends CommunicationChannelOwnerLink> subtypeFor(Class<?> domainObject, CommunicationChannelType communicationChannelType);
    }
    /**
     * Convenience adapter to help implement the {@link SubtypeProvider} SPI; ignores the {@link CommunicationChannelType} passed into
     * {@link #subtypeFor(Class, CommunicationChannelType)}, simply returns the class pair passed into constructor.
     */
    public abstract static class SubtypeProviderAbstract implements SubtypeProvider {
        private final Class<?> ownerDomainType;
        private final Class<? extends CommunicationChannelOwnerLink> ownerSubtype;

        protected SubtypeProviderAbstract(final Class<?> ownerDomainType, final Class<? extends CommunicationChannelOwnerLink> ownerSubtype) {
            this.ownerDomainType = ownerDomainType;
            this.ownerSubtype = ownerSubtype;
        }

        @Override
        public Class<? extends CommunicationChannelOwnerLink> subtypeFor(final Class<?> domainType, CommunicationChannelType communicationChannelType) {
            return domainType.isAssignableFrom(ownerDomainType) ? ownerSubtype : null;
        }
    }

    //endregion

    //region > injected services
    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    BookmarkService bookmarkService;

    @Inject
    List<SubtypeProvider> subtypeProviders;
    //endregion


}
