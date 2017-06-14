package org.isisaddons.module.command.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.command.CommandModule;


@Mixin
public class CommandJdo_childCommands {


    public static class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandJdo_childCommands> { }


    private final CommandJdo commandJdo;
    public CommandJdo_childCommands(final CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }


    @Action(
            domainEvent = ActionDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @CollectionLayout(
            defaultView = "table"
    )
    @MemberOrder(sequence = "100.100")
    public List<CommandJdo> $$() {
        return backgroundCommandRepository.findByParent(commandJdo);
    }


    @javax.inject.Inject
    private BackgroundCommandServiceJdoRepository backgroundCommandRepository;
    
}
