package org.isisaddons.module.command.replay.impl;

import java.util.List;

import javax.inject.Inject;

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

    /**
     * if hit an issue with the command having been replayed, then mark this as in error.
     * this will effectively block the running of any further commands until the adminstrator fixes the issue.
     */
    @Programmatic
    public void analyze(final CommandJdo hwmCommand) {
        final String analysis = analyzeReplay(hwmCommand);

        if (analysis == null) {
            hwmCommand.setReplayState(ReplayState.OK);
        } else {
            hwmCommand.setReplayState(ReplayState.FAILED);
            hwmCommand.setReplayStateFailureReason(analysis);
        }
    }

    private String analyzeReplay(final CommandJdo commandJdo) {

        final CommandDto dto = commandJdo.asDto();

        for (final CommandReplayAnalyser analyser : analysers) {
            String reason = analyser.analyzeReplay(commandJdo, dto);
            if(reason != null) {
                return reason;
            }
        }
        return null;
    }

    @Inject
    List<CommandReplayAnalyser> analysers;

}
