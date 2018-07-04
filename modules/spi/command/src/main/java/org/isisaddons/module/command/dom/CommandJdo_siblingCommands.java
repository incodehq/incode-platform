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
import org.apache.isis.applib.services.command.Command;

import org.isisaddons.module.command.CommandModule;

@Mixin
public class CommandJdo_siblingCommands {


    public static class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandJdo_siblingCommands> { }


    private final CommandJdo commandJdo;
    public CommandJdo_siblingCommands(final CommandJdo commandJdo) {
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
    @MemberOrder(sequence = "100.110")
    public List<CommandJdo> $$() {
        final Command parent = commandJdo.getParent();
        if(parent == null || !(parent instanceof CommandJdo)) {
            return Collections.emptyList();
        }
        final CommandJdo parentJdo = (CommandJdo) parent;
        final List<CommandJdo> siblingCommands = backgroundCommandRepository.findByParent(parentJdo);
        siblingCommands.remove(commandJdo);
        return siblingCommands;
    }


    @javax.inject.Inject
    private BackgroundCommandServiceJdoRepository backgroundCommandRepository;
    
}
