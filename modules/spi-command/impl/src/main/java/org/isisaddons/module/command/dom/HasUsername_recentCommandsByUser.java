package org.isisaddons.module.command.dom;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.HasUsername;

import org.isisaddons.module.command.CommandModule;


@Mixin
public class HasUsername_recentCommandsByUser {


    public static class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<HasUsername_recentCommandsByUser> { }

    private final HasUsername hasUsername;
    public HasUsername_recentCommandsByUser(final HasUsername hasUsername) {
        this.hasUsername = hasUsername;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(name="user", sequence = "200.100")
    public List<CommandJdo> $$() {
        if(hasUsername.getUsername() == null) {
            return Collections.emptyList();
        }
        return commandServiceRepository.findRecentByUser(hasUsername.getUsername());
    }
    public boolean hide$$() {
        return hasUsername.getUsername() == null;
    }


    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;


}
