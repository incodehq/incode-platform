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
package org.incode.module.alias.dom.impl;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.apache.isis.applib.annotation.*;
import org.incode.module.alias.dom.AliasModule;
import org.incode.module.alias.dom.spi.AliasType;
import org.incode.module.alias.dom.spi.AliasTypeRepository;
import org.incode.module.alias.dom.spi.ApplicationTenancyRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

public abstract class T_addAlias<T> {

    //region  > (injected)
    @Inject
    AliasRepository aliasRepository;
    @Inject
    List<ApplicationTenancyRepository> applicationTenancyRepositories;
    @Inject
    List<AliasTypeRepository> aliasTypeRepositories;
    //endregion

    //region > constructor
    private final T aliased;

    public T_addAlias(final T aliased) {
        this.aliased = aliased;
    }

    public Object getAliased() {
        return aliased;
    }
    //endregion

    //region > $$
    public static class DomainEvent extends AliasModule.ActionDomainEvent<T_addAlias> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "fa-plus",
            named = "Add",
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "aliases", sequence = "1")
    public Object $$(
            @Parameter(maxLength = AliasModule.JdoColumnLength.AT_PATH)
            @ParameterLayout(named = "Application tenancy")
            final String applicationTenancyPath,
            final AliasType aliasType,
            @Parameter(maxLength = AliasModule.JdoColumnLength.ALIAS_REFERENCE)
            @ParameterLayout(named = "Alias reference")
            final String alias) {
        aliasRepository.create(this.aliased, applicationTenancyPath, aliasType, alias);
        return this.aliased;
    }

    public Collection<String> choices0$$() {
        final List<String> combined = Lists.newArrayList();
        FluentIterable.from(applicationTenancyRepositories)
                .forEach(applicationTenancyRepository -> {
                    final Collection<String> aliasTypes = applicationTenancyRepository
                            .atPathsFor(this.aliased);
                    if(aliasTypes != null && !aliasTypes.isEmpty()) {
                        combined.addAll(aliasTypes);
                    }
                });
        return combined;
    }

    public Collection<AliasType> choices1$$(final String applicationTenancyPath) {
        final List<AliasType> combined = Lists.newArrayList();
        FluentIterable.from(aliasTypeRepositories)
                .forEach(aliasTypeRepository -> {
                    final Collection<AliasType> aliasTypes = aliasTypeRepository
                            .aliasTypesFor(this.aliased, applicationTenancyPath);
                    if(aliasTypes != null && !aliasTypes.isEmpty()) {
                        combined.addAll(aliasTypes);
                    }
                });
        return combined;
    }


    //endregion

}
