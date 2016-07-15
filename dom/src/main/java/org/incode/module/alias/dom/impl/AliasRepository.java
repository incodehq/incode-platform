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
package org.incode.module.alias.dom.impl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.incode.module.alias.dom.spi.AliasType;

import javax.inject.Inject;
import java.util.List;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Alias.class
)
public class AliasRepository {

    //region > findByAliased (programmatic)
    @Programmatic
    public List<Alias> findByAliased(final Object aliased) {
        if(aliased == null) {
            return null;
        }
        final Bookmark bookmark = bookmarkService.bookmarkFor(aliased);
        if(bookmark == null) {
            return null;
        }
        return repositoryService.allMatches(
                new QueryDefault<>(Alias.class,
                        "findByAliased",
                        "aliasedObjectType", bookmark.getObjectType(),
                        "aliasedIdentifier", bookmark.getIdentifier()));
    }
    //endregion

    //region > create (programmatic)
    @Programmatic
    public Alias create(
            final Object aliased,
            final String atPath,
            final AliasType aliasType,
            final String aliasReference) {

        Class<? extends Alias> linkClass = linkClassFor(aliased);

        final Alias link = repositoryService.instantiate(linkClass);

        link.setAtPath(atPath);
        link.setAliasTypeId(aliasType.getId());
        link.setReference(aliasReference);

        final Bookmark bookmark = bookmarkService.bookmarkFor(aliased);
        link.setAliased(aliased);
        link.setAliasedIdentifier(bookmark.getIdentifier());
        link.setAliasedObjectType(bookmark.getObjectType());

        repositoryService.persist(link);

        return link;
    }

    private Class<? extends Alias> linkClassFor(final Object aliased) {
        Class<?> aliasedDomainClass = aliased.getClass();
        for (LinkProvider linkProvider : linkProviders) {
            Class<? extends Alias> linkClass = linkProvider.linkFor(aliasedDomainClass);
            if(linkClass != null) {
                return linkClass;
            }
        }
        return null;
    }

    //endregion

    //region > remove (programmatic)
    @Programmatic
    public void remove(Alias alias) {
        repositoryService.removeAndFlush(alias);
    }
    //endregion


    //region > LinkProvider SPI

    public interface LinkProvider {
        Class<? extends Alias> linkFor(Class<?> domainObject);
    }
    public abstract static class LinkProviderAbstract implements LinkProvider {
        private final Class<?> aliasedDomainType;
        private final Class<? extends Alias> aliasLinkType;

        protected LinkProviderAbstract(final Class<?> aliasedDomainType, final Class<? extends Alias> aliasLinkType) {
            this.aliasedDomainType = aliasedDomainType;
            this.aliasLinkType = aliasLinkType;
        }

        @Override
        public Class<? extends Alias> linkFor(final Class<?> domainType) {
            return domainType.isAssignableFrom(aliasedDomainType) ? aliasLinkType: null;
        }
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject ServiceRegistry2 serviceRegistry;

    @javax.inject.Inject
    BookmarkService bookmarkService;

    @Inject
    List<LinkProvider> linkProviders;

    //endregion

}
