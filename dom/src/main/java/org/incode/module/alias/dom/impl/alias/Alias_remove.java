package org.incode.module.alias.dom.impl.alias;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.module.alias.dom.api.aliasable.Aliasable;

@Mixin
public class Alias_remove {

    //region > injected services
    @Inject
    AliasRepository aliasRepository;
    //endregion

    //region > constructor
    private final Alias alias;
    public Alias_remove(final Alias alias) {
        this.alias = alias;
    }
    @Programmatic
    public Alias getAlias() {
        return alias;
    }
    //endregion


    public static class DomainEvent extends Alias.ActionDomainEvent<Alias_remove> { }
    @Action(
            domainEvent = DomainEvent.class,
            semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
    )
    public Aliasable $$() {
        final Aliasable aliasable = this.alias.getAliasable();
        aliasRepository.remove(this.alias);
        return aliasable;
    }

}
