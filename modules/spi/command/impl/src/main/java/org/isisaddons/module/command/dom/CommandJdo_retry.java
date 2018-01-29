package org.isisaddons.module.command.dom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Command;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.command.CommandContext;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.schema.cmd.v1.CommandDto;

import org.isisaddons.module.command.CommandModule;

@Mixin(method = "act")
public class CommandJdo_retry<T> {

    public static enum Mode {
        ScheduleNew,
        Reuse
    }

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
    @MemberOrder(name = "exception", sequence = "3")
    public CommandJdo act(final Mode mode) {

        switch (mode) {
        case ScheduleNew:
            final String memento = commandJdo.getMemento();
            final CommandDto dto = jaxbService.fromXml(CommandDto.class, memento);
            backgroundCommandServiceJdo.schedule(
                    dto, commandContext.getCommand(), commandJdo.getTargetClass(), commandJdo.getTargetAction(), commandJdo.getArguments());
            break;
        case Reuse:
            // will cause it to be picked up next time around
            commandJdo.setStartedAt(null);
            commandJdo.setException(null);
            commandJdo.setResult(null);
            commandJdo.setCompletedAt(null);
            break;
        default:
            // shouldn't occur
            throw new IllegalStateException(String.format("Probable framework error, unknown mode: %s", mode));
        }
        return commandJdo;
    }

    public List<Mode> choices0Act() {
        Command.ExecuteIn executeIn = commandJdo.getExecuteIn();
        switch (executeIn){
        case FOREGROUND:
        case BACKGROUND:
            return Arrays.asList(Mode.ScheduleNew, Mode.Reuse);
        case REPLAYABLE:
            return Collections.singletonList(Mode.Reuse);
        default:
            // shouldn't occur
            throw new IllegalStateException(String.format("Probable framework error, unknown executeIn: %s", executeIn));
        }
    }

    public Mode default0Act() {
        return choices0Act().get(0);
    }

    public String disableAct() {
        if (!commandJdo.isCausedException()) {
            return "Only failed commands can be retried";
        }
        if (commandJdo.isLegacyMemento()) {
            return "Only non-legacy commands can be retried";
        }

        return null;
    }

    public boolean hideAct() {
        return commandJdo.getExecuteIn().isExcluded();
    }


    @Inject
    CommandContext commandContext;
    @Inject
    BackgroundCommandServiceJdo backgroundCommandServiceJdo;
    @Inject
    JaxbService jaxbService;

}
