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

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.alias.dom.api.aliasable.AliasType;
import org.incode.module.alias.dom.api.aliasable.Aliasable;
import org.incode.module.alias.dom.spi.aliastype.AliasTypeRepository;
import org.incode.module.alias.dom.spi.aliastype.ApplicationTenancyRepository;

@Mixin
public class Aliasable_addAlias {

    //region  > (injected)
    @Inject
    AliasRepository aliasRepository;
    @Inject
    ApplicationTenancyRepository applicationTenancyRepository;
    @Inject
    AliasTypeRepository aliasTypeRepository;
    //endregion

    //region > constructor
    private final Aliasable aliasable;
    public Aliasable_addAlias(final Aliasable aliasable) {
        this.aliasable = aliasable;
    }

    public Aliasable getAliasable() {
        return aliasable;
    }
    //endregion


    public static class DomainEvent extends Aliasable.ActionDomainEvent<Aliasable_addAlias> { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @MemberOrder(name = "aliases", sequence = "1")
    public Aliasable $$(
            @ParameterLayout()
            final String applicationTenancyPath,
            final AliasType aliasType,
            final String alias) {
        aliasRepository.add(this.aliasable, applicationTenancyPath, aliasType, alias);
        return this.aliasable;
    }

    public Collection<String> choices1$$() {
        return applicationTenancyRepository.atPathsFor(this.aliasable);
    }

    public Collection<AliasType> choices2$$(final String applicationTenancyPath) {
        return aliasTypeRepository.aliasTypesFor(this.aliasable, applicationTenancyPath);
    }
    //endregion

}
