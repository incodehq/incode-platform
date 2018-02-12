package org.isisaddons.module.command.replay.impl;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.value.Clob;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.cmd.v1.CommandsDto;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "isiscommand.CommandReplayOnSlaveService"
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "20.3"
)
public class CommandReplayOnSlaveService {

    public static abstract class PropertyDomainEvent<T>
            extends CommandModule.PropertyDomainEvent<CommandReplayOnSlaveService, T> { }

    public static abstract class CollectionDomainEvent<T>
            extends CommandModule.CollectionDomainEvent<CommandReplayOnSlaveService, T> { }

    public static abstract class ActionDomainEvent
            extends CommandModule.ActionDomainEvent<CommandReplayOnSlaveService> {
    }


    //region > findReplayHwm

    public static class FindReplayHwmOnSlaveDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = FindReplayHwmOnSlaveDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-bath"
    )
    @MemberOrder(sequence="60.1")
    public CommandJdo findReplayHwmOnSlave() {
        return commandServiceJdoRepository.findReplayHwm();
    }

    //endregion

    //region > uploadCommandsToSlave

    public static class UploadCommandsToSlaveDomainEvent extends ActionDomainEvent { }

    @Action(
            command = CommandReification.DISABLED,
            domainEvent = UploadCommandsToSlaveDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "fa-upload"
    )
    @MemberOrder(sequence="60.2")
    public void uploadCommandsToSlave(final Clob commandsDtoAsXml) {
        final CharSequence chars = commandsDtoAsXml.getChars();
        List<CommandDto> commandDtoList;

        try {
            final CommandsDto commandsDto = jaxbService.fromXml(CommandsDto.class, chars.toString());
            commandDtoList = commandsDto.getCommandDto();

        } catch(Exception ex) {
            final CommandDto commandDto = jaxbService.fromXml(CommandDto.class, chars.toString());
            commandDtoList = Collections.singletonList(commandDto);
        }

        for (final CommandDto commandDto : commandDtoList) {
            commandServiceJdoRepository.saveForReplay(commandDto);
        }
    }

    //endregion


    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

    @javax.inject.Inject
    JaxbService jaxbService;


}

