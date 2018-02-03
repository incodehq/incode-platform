package org.isisaddons.module.command.replay.impl;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Objects;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.command.CommandWithDto;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.command.dom.ReplayState;

@DomainService(nature = NatureOfService.DOMAIN)
public class CommandReplayAnalyser {

    /**
     * if hit an issue with the command having been replayed, then mark this as in error.
     * this will effectively block the running of any further commands until the adminstrator fixes the issue.
     */
    @Programmatic
    public void analyze(final CommandJdo hwmCommand) {
        hwmCommand.setReplayState(analyzeReplay(hwmCommand));
    }

    private ReplayState analyzeReplay(final CommandJdo commandJdo) {

        final CommandDto origCommandDto = commandJdo.asDto();

        // see if the number of child commands was the same
        final List<CommandJdo> childCommands =
                commandServiceJdoRepository.findBackgroundCommandsByParent(commandJdo);

        final String origCommandNumChildCommandsStr =
                CommandDtoUtils.getUserData(origCommandDto, CommandJdo.USERDATA_KEY_NUMBER_CHILD_COMMANDS);
        if(origCommandNumChildCommandsStr != null) {
            try {
                final int origCommandChildCommands = Integer.parseInt(origCommandNumChildCommandsStr);
                if (origCommandChildCommands != childCommands.size()) {
                    return ReplayState.NUM_CHILDREN_DIFFER;
                }
            } catch (NumberFormatException ex) {
                return ReplayState.UNABLE_TO_CHECK_CHILDREN;
            }
        }

        // see if the outcome was the same...
        // ... either the same result when replayed
        final String origResultStr =
                CommandDtoUtils.getUserData(origCommandDto, CommandWithDto.USERDATA_KEY_RETURN_VALUE);
        if(origResultStr != null) {
            final String replayedResultStr = commandJdo.getResultStr();
            return Objects.equal(origResultStr, replayedResultStr)
                    ? ReplayState.OK
                    : ReplayState.RESULT_DIFFERS;
        }

        // ... or the same exception when replayed
        final String origException =
                CommandDtoUtils.getUserData(origCommandDto, CommandWithDto.USERDATA_KEY_EXCEPTION);
        if(origException != null) {
            final String replayedException = commandJdo.getException();
            return Objects.equal(origException, replayedException)
                    ? ReplayState.OK
                    : ReplayState.EXCEPTION_DIFFERS;
        }


        // presumably a void action
        return ReplayState.OK;
    }

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

}
