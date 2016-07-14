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

import javax.annotation.PostConstruct;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

import org.incode.module.alias.dom.spi.aliastype.AliasType;
import org.incode.module.alias.dom.impl.alias.Alias;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = AliasLink.class
)
public class AliasLinkRepository {

    //region > init
    PolymorphicAssociationLink.Factory<Alias,Object,AliasLink,AliasLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = serviceRegistry.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        Alias.class,
                        Object.class,
                        AliasLink.class,
                        AliasLink.InstantiateEvent.class
                ));

    }
    //endregion

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

    @Programmatic
    public boolean supports(final Object classifiable) {
        return linkFactory.supportsLink(classifiable);
    }

    @Programmatic
    public AliasLink createLink(final Alias alias, final Object aliased) {
        final AliasLink link = linkFactory.createLink(alias, aliased);

        sync(alias, link);

        return link;
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
