/*
 *
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
package org.incode.module.alias.dom.impl.alias;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.repository.RepositoryService;

import org.incode.module.alias.dom.spi.AliasType;
import org.incode.module.alias.dom.impl.aliaslink.AliasLink;
import org.incode.module.alias.dom.impl.aliaslink.AliasLinkRepository;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Alias.class
)
public class AliasRepository {

    //region > findByAliased (programmatic)
    @Programmatic
    public List<Alias> findByAliased(final Object aliased) {
        final List<AliasLink> links = aliasLinkRepository.findByAliased(aliased);
        return toAliases(links);
    }

    //endregion

    //region > add (programmatic)
    @Programmatic
    public Alias add(
            final Object aliased,
            final String atPath,
            final AliasType aliasType,
            final String aliasReference) {
        final Alias alias = repositoryService.instantiate(Alias.class);

        alias.setAtPath(atPath);
        alias.setAliasTypeId(aliasType.getId());
        alias.setReference(aliasReference);

        // must be set after atPath and aliasTypeId, because these are sync'ed from the alias onto the AliasLink.
        alias.setAliased(aliased);

        repositoryService.persist(alias);

        return alias;
    }
    //endregion

    //region > remove (programmatic)
    @Programmatic
    public void remove(Alias alias) {
        final AliasLink link = aliasLinkRepository.findByAlias(alias);
        repositoryService.removeAndFlush(link);
        repositoryService.removeAndFlush(alias);
    }
    //endregion

    //region > helpers: toAliases
    static List<Alias> toAliases(final List<AliasLink> links) {
        return Lists.newArrayList(
                Iterables.transform(links, AliasLink.Functions.alias()));
    }
    //endregion

    //region > injected
    @Inject
    AliasLinkRepository aliasLinkRepository;
    @Inject
    RepositoryService repositoryService;
    //endregion

}
