package org.isisaddons.module.command.dom;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Command;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.command.CommandContext;
import org.apache.isis.applib.services.jaxb.JaxbService;

import org.isisaddons.module.command.CommandModule;

@Mixin(method = "act")
public class CommandJdo_exclude<T> {

    private final CommandJdo commandJdo;

    //region > constructor
    public CommandJdo_exclude(CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }
    //endregion


    public static class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandJdo_exclude> { }
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public CommandJdo act() {

        commandJdo.setExecuteIn(Command.ExecuteIn.EXCLUDED);
        return commandJdo;
    }

    public boolean hideAct() {
        return commandNotReplayable();
    }

    public String disableAct() {
        if (commandNotReplayable()) {
            return "Only replayable commands can be excluded";
        }
        if (!commandJdo.isCausedException()) {
            return "Only failed commands can be excluded";
        }

        return null;
    }

    private boolean commandNotReplayable() {
        boolean replayable = commandJdo.getExecuteIn().isReplayable();
        return !replayable;
    }

}
