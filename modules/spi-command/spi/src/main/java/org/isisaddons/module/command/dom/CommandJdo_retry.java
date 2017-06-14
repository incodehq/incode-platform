package org.isisaddons.module.command.dom;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.command.CommandContext;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.isisaddons.module.command.CommandModule;

import javax.inject.Inject;

@Mixin
public class CommandJdo_retry<T> {


    private final CommandJdo commandJdo;

    //region > constructor
    public CommandJdo_retry(CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }
    //endregion


    public static class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandJdo_retry> { }
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public CommandJdo $$() {

        final String memento = commandJdo.getMemento();
        final CommandDto dto = jaxbService.fromXml(CommandDto.class, memento);
        backgroundCommandServiceJdo.schedule(
                dto, commandContext.getCommand(), commandJdo.getTargetClass(), commandJdo.getTargetAction(), commandJdo.getArguments());

        return commandJdo;
    }

    public String disable$$() {
        if (!commandJdo.isCausedException()) {
            return "Only failed commands can be retried";
        }
        if (commandJdo.isLegacyMemento()) {
            return "Only non-legacy commands can be retried";
        }

        return null;
    }


    @Inject
    CommandContext commandContext;
    @Inject
    BackgroundCommandServiceJdo backgroundCommandServiceJdo;
    @Inject
    JaxbService jaxbService;

}
