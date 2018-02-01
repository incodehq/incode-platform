package org.isisaddons.module.command.replay.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.services.command.Command;
import org.apache.isis.core.runtime.services.background.BackgroundCommandExecution;
import org.apache.isis.core.runtime.system.transaction.IsisTransactionManager;

import org.isisaddons.module.command.dom.ReplayableCommandServiceJdoRepository;

public class ReplayableCommandExecutionFromReplayableCommandServiceJdo
        extends BackgroundCommandExecution {

    private final static Logger LOG =
            LoggerFactory.getLogger(ReplayableCommandExecutionFromReplayableCommandServiceJdo.class);

    public ReplayableCommandExecutionFromReplayableCommandServiceJdo() {
        super(OnExceptionPolicy.QUIT, SudoPolicy.SWITCH);
    }

    @Override
    protected List<? extends Command> findBackgroundCommandsToExecute() {
        return repository.findReplayableCommandsNotYetStartedUnlessBlocked();
    }

    @Override
    protected Boolean executeCommand(
            final IsisTransactionManager transactionManager,
            final Command command) {

        if(!tickingClockService.isInitialized()) {
            LOG.debug("TickingClockService is not yet initialized - skipping");
            return false;
        }

        return tickingClockService.at(command.getTimestamp(),
                () -> super.doExecuteCommand(transactionManager, command));
    }

    @javax.inject.Inject
    TickingClockService tickingClockService;

    @javax.inject.Inject
    ReplayableCommandServiceJdoRepository repository;
}