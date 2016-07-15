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

import org.apache.isis.applib.annotation.*;
import org.incode.module.alias.dom.AliasModule;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public abstract class T_removeAlias<T> {

    //region  > (injected)
    @Inject
    AliasRepository aliasRepository;
    //endregion

    //region > constructor
    private final T aliased;

    public T_removeAlias(final T aliased) {
        this.aliased = aliased;
    }

    public T getAliased() {
        return aliased;
    }
    //endregion

    //region > $$

    public static class DomainEvent extends AliasModule.ActionDomainEvent<T_removeAlias> { } { }

    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(
            cssClassFa = "fa-minus",
            named = "Remove",
            contributed = Contributed.AS_ACTION
    )
    @MemberOrder(name = "aliases", sequence = "2")
    public Object $$(final Alias link) {
        aliasRepository.remove(link);
        return this.aliased;
    }

    public String disable$$(final Alias alias) {
        return choices0$$().isEmpty() ? "No aliases to remove" : null;
    }

    public List<Alias> choices0$$() {
        return this.aliased != null ? aliasRepository.findByAliased(this.aliased): Collections.emptyList();
    }

    public Alias default0$$() {
        return firstOf(choices0$$());
    }

    //endregion


    //region > helpers
    static <T> T firstOf(final List<T> list) {
        return list.isEmpty()? null: list.get(0);
    }
    //endregion

}
