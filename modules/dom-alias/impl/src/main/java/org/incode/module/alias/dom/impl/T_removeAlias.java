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
