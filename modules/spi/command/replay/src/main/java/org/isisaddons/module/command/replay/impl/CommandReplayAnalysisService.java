package org.isisaddons.module.command.replay.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.schema.cmd.v1.CommandDto;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.ReplayState;
import org.isisaddons.module.command.replay.spi.CommandReplayAnalyser;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CommandReplayAnalysisService {

    private final static Logger LOG =
            LoggerFactory.getLogger(ReplayableCommandExecution.class);

    /**
     * if hit an issue with the command having been replayed, then mark this as in error.
     * this will effectively block the running of any further commands until the adminstrator fixes the issue.
     */
    @Programmatic
    public void analyse(final CommandJdo hwmCommand) {
        final String analysis = analyseReplay(hwmCommand);

        if (analysis == null) {
            hwmCommand.setReplayState(ReplayState.OK);
        } else {
            hwmCommand.setReplayState(ReplayState.FAILED);
            hwmCommand.setReplayStateFailureReason(trimmed(analysis, 255));
        }
    }

    private String analyseReplay(final CommandJdo commandJdo) {

        final CommandDto dto = commandJdo.asDto();

        for (final CommandReplayAnalyser analyser : analysers) {
            try {
                String reason = analyser.analyzeReplay(commandJdo, dto);
                if (reason != null) {
                    return reason;
                }
            } catch(Exception ex) {
                final String className = analyser.getClass().getName();
                LOG.warn("{} threw exception: ", className, ex);
                return className + " threw exception: " + ex.getMessage();
            }
        }
        return null;
    }

    static String trimmed(final String str, final int lengthOfField) {
        if(str == null) { return null; }
        if(str.length() > lengthOfField) {
            return str.substring(0, lengthOfField - 3) + "...";
        }
        return str;
    }

    @Inject
    List<CommandReplayAnalyser> analysers;

}
