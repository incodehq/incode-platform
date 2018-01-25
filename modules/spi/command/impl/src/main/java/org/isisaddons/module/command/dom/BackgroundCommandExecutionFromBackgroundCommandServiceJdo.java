package org.isisaddons.module.command.dom;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.services.command.Command;
import org.apache.isis.core.runtime.services.background.BackgroundCommandExecution;

/**
 * If used, ensure that <code>org.apache.isis.module:isis-module-background</code> is also included on classpath.
 */
public class BackgroundCommandExecutionFromBackgroundCommandServiceJdo extends BackgroundCommandExecution {

    @SuppressWarnings("unused")
    private final static Logger LOG = LoggerFactory.getLogger(BackgroundCommandExecutionFromBackgroundCommandServiceJdo.class);

    @Override
    protected List<? extends Command> findBackgroundCommandsToExecute() {
        final List<CommandJdo> commands = backgroundCommandRepository.findBackgroundOrReplayableCommandsNotYetStarted();
        LOG.debug("Found {} to execute", commands.size());
        return commands; 
    }


    @javax.inject.Inject
    BackgroundCommandServiceJdoRepository backgroundCommandRepository;
}