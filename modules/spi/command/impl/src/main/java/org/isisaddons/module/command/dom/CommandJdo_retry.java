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
public class CommandJdo_retry {

    public static enum Mode {
        SCHEDULE_NEW,
        REUSE
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
        case SCHEDULE_NEW:
            final String memento = commandJdo.getMemento();
            final CommandDto dto = jaxbService.fromXml(CommandDto.class, memento);
            backgroundCommandServiceJdo.schedule(
                    dto, commandContext.getCommand(), commandJdo.getTargetClass(), commandJdo.getTargetAction(), commandJdo.getArguments());
            break;
        case REUSE:
            // will cause it to be picked up next time around
            commandJdo.setStartedAt(null);
            commandJdo.setException(null);
            commandJdo.setResult(null);
            commandJdo.setCompletedAt(null);
            commandJdo.setReplayState(null);
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
            return Arrays.asList(Mode.SCHEDULE_NEW, Mode.REUSE);
        case REPLAYABLE:
            return Collections.singletonList(Mode.REUSE);
        default:
            // shouldn't occur
            throw new IllegalStateException(String.format("Probable framework error, unknown executeIn: %s", executeIn));
        }
    }

    public Mode default0Act() {
        return choices0Act().get(0);
    }

    public boolean hideAct() {
        return !commandJdo.isComplete();
    }


    @Inject
    CommandContext commandContext;
    @Inject
    BackgroundCommandServiceJdo backgroundCommandServiceJdo;
    @Inject
    JaxbService jaxbService;

}
