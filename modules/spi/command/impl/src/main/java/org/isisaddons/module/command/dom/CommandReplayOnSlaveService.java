package org.isisaddons.module.command.dom;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
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


    //region > uploadCommandsToSlave

    public static class UploadCommandsToSlaveDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = UploadCommandsToSlaveDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "fa-upload"
    )
    @MemberOrder(sequence="60")
    public void uploadCommandsToSlave(final Clob commandsDtoAsXml) {
        final CharSequence chars = commandsDtoAsXml.getChars();
        final CommandsDto commandsDto = jaxbService.fromXml(CommandsDto.class, chars.toString());
        final List<CommandDto> commandDtoList = commandsDto.getCommandDto();
        for (final CommandDto commandDto : commandDtoList) {
            commandServiceRepository.saveForReplay(commandDto);
        }
    }

    //endregion

    //region > findReplayHwm

    public static class FindReplayHwmOnSlaveDomainEvent extends ActionDomainEvent { }

    @Action(
            domainEvent = FindReplayHwmOnSlaveDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-bath"
    )
    @MemberOrder(sequence="60")
    public CommandJdo findReplayHwmOnSlave() {
        return commandServiceRepository.findReplayHwm();
    }

    //endregion

    @javax.inject.Inject
    CommandServiceJdoRepository commandServiceRepository;

    @javax.inject.Inject
    JaxbService jaxbService;

}

