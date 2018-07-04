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

        final String masterExceptionTrimmed = trimmed(masterException);
        final String replayedExceptionTrimmed = trimmed(replayedException);
        return Objects.equal(masterExceptionTrimmed, replayedExceptionTrimmed)
                ? null
                : String.format("Exceptions differ.  Master was '%s'", masterException);
    }

    private String trimmed(final String str) {
        return withoutWhitespace(initialPartOfStackTrace(str));
    }

    // we only look at beginning of the stack trace because the latter part will differ when replayed
    private String initialPartOfStackTrace(final String str) {
        final int toInspectOfStackTrace = 500;
        return str.length() > toInspectOfStackTrace ? str.substring(0, toInspectOfStackTrace) : str;
    }

    private String withoutWhitespace(final String s) {
        return s.replaceAll("\\s", "");
    }

}
