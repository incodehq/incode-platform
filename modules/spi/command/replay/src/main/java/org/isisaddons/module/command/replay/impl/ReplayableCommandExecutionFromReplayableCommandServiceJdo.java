package org.isisaddons.module.command.replay.impl;

import java.util.List;

import org.apache.isis.applib.services.command.Command;
import org.apache.isis.core.runtime.services.background.BackgroundCommandExecution;

import org.isisaddons.module.command.dom.ReplayableCommandServiceJdoRepository;

public class ReplayableCommandExecutionFromReplayableCommandServiceJdo extends BackgroundCommandExecution {

    public ReplayableCommandExecutionFromReplayableCommandServiceJdo() {
        super(OnExceptionPolicy.QUIT, SudoPolicy.SWITCH);
    }

    @Override
    protected List<? extends Command> findBackgroundCommandsToExecute() {
        return repository.findReplayableCommandsNotYetStartedUnlessBlocked();
    }

    @javax.inject.Inject
    ReplayableCommandServiceJdoRepository repository;
}