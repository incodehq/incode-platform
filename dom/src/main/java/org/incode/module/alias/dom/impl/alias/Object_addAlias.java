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

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.alias.dom.AliasModule;
import org.incode.module.alias.dom.spi.AliasType;
import org.incode.module.alias.dom.spi.AliasTypeRepository;
import org.incode.module.alias.dom.spi.ApplicationTenancyRepository;

@Mixin
public class Object_addAlias {

    //region  > (injected)
    @Inject
    AliasRepository aliasRepository;
    @Inject
    List<ApplicationTenancyRepository> applicationTenancyRepositories;
    @Inject
    List<AliasTypeRepository> aliasTypeRepositories;
    //endregion

    //region > constructor
    private final Object aliased;
    public Object_addAlias(final Object aliased) {
        this.aliased = aliased;
    }

    public Object getAliased() {
        return aliased;
    }
    //endregion

    //region > $$
    public static class DomainEvent extends AliasModule.ActionDomainEvent<Object_addAlias> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "fa-plus",
            named = "Add"
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
        aliasRepository.add(this.aliased, applicationTenancyPath, aliasType, alias);
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

    public boolean hide$$() {
        return !aliasRepository.supports(this.aliased);
    }

    //endregion

}
