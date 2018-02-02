package org.isisaddons.module.command.replay.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.conmap.spi.CommandDtoProcessorService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.CommandWithDto;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;

/**
 * Adds the number of background commands that this command has, add into the
 * {@link CommandJdo#USERDATA_KEY_NUMBER_CHILD_COMMANDS userdata} of its
 * {@link CommandWithDto#asDto() corresponding} {@link CommandDto DTO}.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class CountBackgroundCommandsDtoProcessorService implements CommandDtoProcessorService {

    @Override
    public CommandDto process(final Command command, final CommandDto commandDto) {
        if(command instanceof CommandJdo) {
            final CommandJdo commandJdo = (CommandJdo) command;

            final List<CommandJdo> backgroundCommands =
                    commandServiceJdoRepository.findBackgroundCommandsByParent(commandJdo);

            CommandDtoUtils.setUserData(commandDto,
                    CommandJdo.USERDATA_KEY_NUMBER_CHILD_COMMANDS, ""+backgroundCommands.size());
        }
        return commandDto;
    }

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;
}
