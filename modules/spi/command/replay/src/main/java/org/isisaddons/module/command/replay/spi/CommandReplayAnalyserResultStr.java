package org.isisaddons.module.command.replay.spi;

import com.google.common.base.Objects;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.CommandWithDto;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

import org.isisaddons.module.command.dom.CommandJdo;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommandReplayAnalyserResultStr extends CommandReplayAnalyserAbstract {

    public static final String ANALYSIS_KEY = "isis.services."
            + CommandReplayAnalyserResultStr.class.getSimpleName() +
            ".analysis";

    public CommandReplayAnalyserResultStr() {
        super(ANALYSIS_KEY);
    }

    protected String doAnalyzeReplay(final Command command, final CommandDto dto) {

        if (!(command instanceof CommandJdo)) {
            return null;
        }

        final CommandJdo commandJdo = (CommandJdo) command;

        // see if the outcome was the same...
        // ... either the same result when replayed
        final String masterResultStr =
                CommandDtoUtils.getUserData(dto, CommandWithDto.USERDATA_KEY_RETURN_VALUE);
        if (masterResultStr == null) {
            return null;
        }

        final String slaveResultStr = commandJdo.getResultStr();
        return Objects.equal(masterResultStr, slaveResultStr)
                ? null
                : String.format("Results differ.  Master was '%s'", masterResultStr);
    }

}
