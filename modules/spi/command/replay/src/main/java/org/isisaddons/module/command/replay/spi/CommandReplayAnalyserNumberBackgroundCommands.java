package org.isisaddons.module.command.replay.spi;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.conmap.spi.CommandDtoProcessorService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommandReplayAnalyserNumberBackgroundCommands
        extends CommandReplayAnalyserAbstract
        implements CommandDtoProcessorService {

    public static final String ANALYSIS_KEY = "isis.services."
            + CommandReplayAnalyserNumberBackgroundCommands.class.getSimpleName()
            + ".analysis";

    public static final String USERDATA_KEY_NUMBER_BACKGROUND_COMMANDS = "numberBackgroundCommands";

    public CommandReplayAnalyserNumberBackgroundCommands() {
        super(ANALYSIS_KEY);
    }

    /**
     * Hook for the master, enriches the DTO.
     */
    @Override
    public CommandDto process(final Command command, final CommandDto commandDto) {
        if(command instanceof CommandJdo) {
            final CommandJdo commandJdo = (CommandJdo) command;

            final List<CommandJdo> backgroundCommands =
                    commandServiceJdoRepository.findBackgroundCommandsByParent(commandJdo);

            CommandDtoUtils.setUserData(commandDto,
                    USERDATA_KEY_NUMBER_BACKGROUND_COMMANDS, ""+backgroundCommands.size());
        }
        return commandDto;
    }

    /**
     * Hook for the slave.
     */
    protected String doAnalyzeReplay(final Command command, final CommandDto dto) {
        if (!(command instanceof CommandJdo)) {
            return null;
        }

        final String masterNumBackgroundCommandsStr =
                CommandDtoUtils.getUserData(dto, USERDATA_KEY_NUMBER_BACKGROUND_COMMANDS);

        if (masterNumBackgroundCommandsStr == null) {
            return null;
        }

        final int masterNumBackgroundCommands;
        try {
            masterNumBackgroundCommands = Integer.parseInt(masterNumBackgroundCommandsStr);

        } catch (NumberFormatException ex) {
            return String.format(
                    "Unable to check number of background commands; "
                            + "could not parse '%s' (value of '%s' userdata) in XML",
                    masterNumBackgroundCommandsStr, USERDATA_KEY_NUMBER_BACKGROUND_COMMANDS);
        }


        final CommandJdo commandJdo = (CommandJdo) command;
        final List<CommandJdo> backgroundCommands =
                commandServiceJdoRepository.findBackgroundCommandsByParent(commandJdo);

        final int slaveNumBackgroundCommands = backgroundCommands.size();
        if (masterNumBackgroundCommands == slaveNumBackgroundCommands) {
            return null;
        }

        return String.format("Number of background commands differs.  Master was %d (slave is %d)",
                masterNumBackgroundCommands, slaveNumBackgroundCommands);
    }

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

}
