package org.isisaddons.module.command.replay.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.services.command.Command;
import org.apache.isis.core.runtime.services.background.BackgroundCommandExecution;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.ReplayableCommandServiceJdoRepository;

public class ReplicatedCommandExecutionFromReplayableCommandServiceJdo extends BackgroundCommandExecution {

    @SuppressWarnings("unused")
    private final static Logger LOG = LoggerFactory.getLogger(ReplicatedCommandExecutionFromReplayableCommandServiceJdo.class);

    public ReplicatedCommandExecutionFromReplayableCommandServiceJdo() {
        super(OnExceptionPolicy.QUIT);
    }

    @Override
    protected List<? extends Command> findBackgroundCommandsToExecute() {
        final List<CommandJdo> commands = repository.findReplayableCommandsNotYetStartedUnlessBlocked();
        LOG.debug("Found {} to execute", commands.size());
        return commands; 
    }


    @javax.inject.Inject
    ReplayableCommandServiceJdoRepository repository;
}