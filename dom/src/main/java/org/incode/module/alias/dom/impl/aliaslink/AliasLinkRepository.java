/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.incode.module.alias.dom.impl.aliaslink;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.alias.dom.spi.AliasType;
import org.incode.module.alias.dom.impl.alias.Alias;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = AliasLink.class
)
public class AliasLinkRepository {

    //region > findByAlias (programmatic)
    @Programmatic
    public AliasLink findByAlias(final Alias alias) {
        return repositoryService.firstMatch(
                new QueryDefault<>(AliasLink.class,
                        "findByAlias",
                        "alias", alias));
    }
    //endregion


    //region > findByAliased (programmatic)
    @Programmatic
    public List<AliasLink> findByAliased(final Object aliased) {
        if(aliased == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliased);
        if(bookmark == null) {
            return null;
        }
        return repositoryService.allMatches(
                new QueryDefault<>(AliasLink.class,
                        "findByAliased",
                        "aliasedObjectType", bookmark.getObjectType(),
                        "aliasedIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > findByAliasedAndAtPath
    @Programmatic
    public List<AliasLink> findByAliasedAndAtPath(
            final Object aliased,
            final String atPath) {
        if(aliased == null) {
            return null;
        }
        if(atPath == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliased);
        if(bookmark == null) {
            return null;
        }
        return repositoryService.allMatches(
                new QueryDefault<>(AliasLink.class,
                        "findByAliasedAndAtPath",
                        "aliasedObjectType", bookmark.getObjectType(),
                        "aliasedIdentifier", bookmark.getIdentifier(),
                        "atPath", atPath));
    }
    //endregion

    //region > findByAliasedAndAliasType
    public List<AliasLink> findByAliasedAndAliasType(
            final Object aliased,
            final AliasType aliasType) {
        if(aliased == null) {
            return null;
        }
        if(aliasType == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliased);
        if(bookmark == null) {
            return null;
        }
        return repositoryService.allMatches(
                new QueryDefault<>(AliasLink.class,
                        "findByAliasedAndAtPath",
                        "aliasedObjectType", bookmark.getObjectType(),
                        "aliasedIdentifier", bookmark.getIdentifier(),
                        "aliasTypeId", aliasType.getId()));
    }
    //endregion

    //region > findByAliasedAndAtPathAndAliasType
    public AliasLink findByAliasedAndAtPathAndAliasType(
            final Object aliased,
            final String atPath,
            final AliasType aliasType) {
        if(aliased == null) {
            return null;
        }
        if(atPath == null) {
            return null;
        }
        if(aliasType == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliased);
        if(bookmark == null) {
            return null;
        }
        return repositoryService.firstMatch(
                new QueryDefault<>(AliasLink.class,
                        "findByAliasedAndAtPathAndAliasType",
                        "aliasedObjectType", bookmark.getObjectType(),
                        "aliasedIdentifier", bookmark.getIdentifier(),
                        "atPath", atPath,
                        "aliasTypeId", aliasType.getId()));
    }
    //endregion

    //region > createLink (programmatic)

    public interface LinkProviderSpi {
        Class<? extends AliasLink> linkFor(Class<?> domainObject);
    }
    public abstract static class LinkProviderAbstract implements AliasLinkRepository.LinkProviderSpi {
        private final Class<?> aliasedDomainType;
        private final Class<? extends AliasLink> aliasLinkType;

        protected LinkProviderAbstract(final Class<?> aliasedDomainType, final Class<? extends AliasLink> aliasLinkType) {
            this.aliasedDomainType = aliasedDomainType;
            this.aliasLinkType = aliasLinkType;
        }

        @Override
        public Class<? extends AliasLink> linkFor(final Class<?> domainType) {
            return domainType.isAssignableFrom(aliasedDomainType) ? aliasLinkType: null;
        }
    }

    @Inject
    List<LinkProviderSpi> linkProviders;

    @Programmatic
    public AliasLink createLink(final Alias alias, final Object aliased) {
        Class<? extends AliasLink> linkClass = linkClassFor(aliased);

        final AliasLink link = repositoryService.instantiate(linkClass);
        Bookmark bookmark = bookmarkService.bookmarkFor(aliased);

        link.setAliasedIdentifier(bookmark.getIdentifier());
        link.setAliasedObjectType(bookmark.getObjectType());
        link.setAliased(aliased);

        sync(alias, link);

        repositoryService.persist(link);

        return link;
    }

    Class<? extends AliasLink> linkClassFor(final Object aliased) {
        Class<?> aliasedDomainClass = aliased.getClass();
        for (LinkProviderSpi linkProvider : linkProviders) {
            Class<? extends AliasLink> linkClass = linkProvider.linkFor(aliasedDomainClass);
            if(linkClass != null) {
                return linkClass;
            }
        }
        return null;
    }

    //endregion

    //region > updateLink
    @Programmatic
    public void updateLink(final Alias alias) {
        final AliasLink link = findByAlias(alias);
        sync(alias, link);
    }
    //endregion

    //region > helpers (sync)

    /**
     * copy over details from the {@link Alias#} to the {@link AliasLink} (derived propoerties to support querying).
     */
    void sync(final Alias alias, final AliasLink link) {
        if(link == null) {
            return;
        }
        link.setAlias(alias);
        link.setAliasTypeId(alias.getAliasTypeId());
        link.setAtPath(alias.getAtPath());
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject ServiceRegistry2 serviceRegistry;

    @javax.inject.Inject
    BookmarkService bookmarkService;

    //endregion

}
