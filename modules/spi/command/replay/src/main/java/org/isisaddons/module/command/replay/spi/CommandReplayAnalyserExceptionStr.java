package org.isisaddons.module.command.replay.spi;

import com.google.common.base.Objects;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.CommandWithDto;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommandReplayAnalyserExceptionStr extends CommandReplayAnalyserAbstract {

    public static final String ANALYSIS_KEY = "isis.services."
            + CommandReplayAnalyserExceptionStr.class.getSimpleName()
            + ".analysis";

    public CommandReplayAnalyserExceptionStr() {
        super(ANALYSIS_KEY);
    }

    protected String doAnalyzeReplay(final Command command, final CommandDto dto) {

        final String masterException =
                CommandDtoUtils.getUserData(dto, CommandWithDto.USERDATA_KEY_EXCEPTION);
        if (masterException == null) {
            return null;
        }

        final String replayedException = command.getException();
        return Objects.equal(masterException, replayedException)
                ? null
                : String.format("Exceptions differ.  Master was '%s'", masterException);
    }

}
