package org.isisaddons.module.command.replay.impl.mixins;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CommandPersistence;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Clob;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.replay.impl.CommandReplayOnMasterService;
import org.isisaddons.module.command.replay.impl.StatusException;

@Mixin(method = "act")
public class CommandJdo_download {

    private final CommandJdo commandJdo;

    //region > constructor
    public CommandJdo_download(CommandJdo commandJdo) {
        this.commandJdo = commandJdo;
    }
    //endregion


    public static class ActionDomainEvent extends CommandModule.ActionDomainEvent<CommandJdo_download> { }
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT,
            domainEvent = ActionDomainEvent.class,
            commandPersistence = CommandPersistence.NOT_PERSISTED
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION,
            cssClassFa = "fa-download",
            position = ActionLayout.Position.PANEL
    )
    @MemberOrder(name = "arguments", sequence = "1")
    public Clob act(
            @ParameterLayout(named="Filename prefix")
            final String fileNamePrefix) throws StatusException {
        return commandReplayOnMasterService.downloadCommandById(commandJdo.getTransactionId(), fileNamePrefix);
    }

    public String default0Act() {
        return "command";
    }


    @Inject
    CommandReplayOnMasterService commandReplayOnMasterService;
}
