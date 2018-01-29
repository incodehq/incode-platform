package org.isisaddons.module.command.dom;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.message.MessageService;

import org.isisaddons.module.command.CommandModule;

@Mixin(method = "act")
public class CommandJdo_nextFailed<T> {

    private final CommandJdo commandJdo;

    //region > constructor
    public CommandJdo_nextFailed(CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }
    //endregion


    public static class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandJdo_nextFailed> { }
    @Action(
            semantics = SemanticsOf.SAFE,
            domainEvent = ActionDomainEvent.class
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "fa-next"
    )
    @MemberOrder(name = "exception", sequence = "4")
    public CommandJdo act() {

        final List<CommandJdo> commands = findAnyFailedReplayableCommands();
        if (commands.isEmpty()) {
            // shouldn't happen because of the disableXxx guard below.
            messageService.informUser("No replayable commands are now failing");
            return this.commandJdo;
        }

        return commands.get(0);
    }

    public String disableAct() {
        return findAnyFailedReplayableCommands().isEmpty()
                ? null
                : "No replayable commands currently failing";
    }

    private List<CommandJdo> findAnyFailedReplayableCommands() {
        return commandServiceJdoRepository.findAnyFailedReplayableCommands();
    }

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;
    @Inject
    MessageService messageService;
}
