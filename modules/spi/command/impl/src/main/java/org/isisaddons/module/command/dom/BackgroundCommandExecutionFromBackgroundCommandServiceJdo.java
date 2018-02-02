package org.isisaddons.module.command.dom;

import java.util.List;

import org.apache.isis.applib.services.command.Command;
import org.apache.isis.core.runtime.services.background.BackgroundCommandExecution;

public class BackgroundCommandExecutionFromBackgroundCommandServiceJdo extends BackgroundCommandExecution {

    public BackgroundCommandExecutionFromBackgroundCommandServiceJdo() {
        super(SudoPolicy.NO_SWITCH);
    }

    @Override
    protected List<? extends Command> findBackgroundCommandsToExecute() {
        return backgroundCommandRepository.findBackgroundCommandsNotYetStarted();
    }

    @javax.inject.Inject
    BackgroundCommandServiceJdoRepository backgroundCommandRepository;
}