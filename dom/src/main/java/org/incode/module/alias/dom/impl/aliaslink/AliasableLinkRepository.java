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

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

import org.incode.module.alias.dom.api.aliasable.AliasType;
import org.incode.module.alias.dom.api.aliasable.Aliasable;
import org.incode.module.alias.dom.impl.alias.Alias;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = AliasableLink.class
)
public class AliasableLinkRepository {

    //region > init
    PolymorphicAssociationLink.Factory<Alias,Aliasable,AliasableLink,AliasableLink.InstantiateEvent> linkFactory;

    @PostConstruct
    public void init() {
        linkFactory = container.injectServicesInto(
                new PolymorphicAssociationLink.Factory<>(
                        this,
                        Alias.class,
                        Aliasable.class,
                        AliasableLink.class,
                        AliasableLink.InstantiateEvent.class
                ));

    }
    //endregion

    //region > findByAlias (programmatic)
    @Programmatic
    public AliasableLink findByAlias(final Alias alias) {
        return container.firstMatch(
                new QueryDefault<>(AliasableLink.class,
                        "findByAlias",
                        "alias", alias));
    }
    //endregion


    //region > findByAliasable (programmatic)
    @Programmatic
    public List<AliasableLink> findByAliasable(final Aliasable aliasable) {
        if(aliasable == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliasable);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(AliasableLink.class,
                        "findByAliasable",
                        "aliasableObjectType", bookmark.getObjectType(),
                        "aliasableIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > findByAliasableAndAtPath
    @Programmatic
    public List<AliasableLink> findByAliasableAndAtPath(
            final Aliasable aliasable,
            final String atPath) {
        if(aliasable == null) {
            return null;
        }
        if(atPath == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliasable);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(AliasableLink.class,
                        "findByAliasAndAtPath",
                        "aliasableObjectType", bookmark.getObjectType(),
                        "aliasableIdentifier", bookmark.getIdentifier(),
                        "atPath", atPath));
    }
    //endregion

    //region > findByAliasableAndAliasType
    public List<AliasableLink> findByAliasableAndAliasType(
            final Aliasable aliasable,
            final AliasType aliasType) {
        if(aliasable == null) {
            return null;
        }
        if(aliasType == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliasable);
        if(bookmark == null) {
            return null;
        }
        return container.allMatches(
                new QueryDefault<>(AliasableLink.class,
                        "findByAliasAndAtPath",
                        "aliasableObjectType", bookmark.getObjectType(),
                        "aliasableIdentifier", bookmark.getIdentifier(),
                        "aliasTypeId", aliasType.getId()));
    }
    //endregion

    //region > findByAliasableAndAtPathAndAliasType
    public AliasableLink findByAliasableAndAtPathAndAliasType(
            final Aliasable aliasable,
            final String atPath,
            final AliasType aliasType) {
        if(aliasable == null) {
            return null;
        }
        if(atPath == null) {
            return null;
        }
        if(aliasType == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliasable);
        if(bookmark == null) {
            return null;
        }
        return container.firstMatch(
                new QueryDefault<>(AliasableLink.class,
                        "findByAliasAndAtPath",
                        "aliasableObjectType", bookmark.getObjectType(),
                        "aliasableIdentifier", bookmark.getIdentifier(),
                        "atPath", atPath,
                        "aliasTypeId", aliasType.getId()));
    }
    //endregion

    //region > createLink (programmatic)
    @Programmatic
    public AliasableLink createLink(final Alias alias, final Aliasable aliasable) {
        final AliasableLink link = linkFactory.createLink(alias, aliasable);

        sync(alias, link);

        return link;
    }

    //endregion

    //region > updateLink
    @Programmatic
    public void updateLink(final Alias alias) {
        final AliasableLink link = findByAlias(alias);
        sync(alias, link);
    }
    //endregion

    //region > helpers (sync)

    /**
     * copy over details from the {@link Alias#} to the {@link AliasableLink} (derived propoerties to support querying).
     */
    void sync(final Alias alias, final AliasableLink link) {
        if(link == null) {
            return;
        }
        link.setAliasTypeId(alias.getAliasTypeId());
        link.setAtPath(alias.getAtPath());
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    BookmarkService bookmarkService;

    //endregion

}
