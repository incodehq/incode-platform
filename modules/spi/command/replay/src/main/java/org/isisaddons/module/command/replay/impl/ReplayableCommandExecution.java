package org.isisaddons.module.command.replay.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.services.command.CommandExecutorService;
import org.apache.isis.core.runtime.services.background.CommandExecutionAbstract;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;
import org.apache.isis.core.runtime.system.transaction.IsisTransactionManager;
import org.apache.isis.schema.cmd.v1.CommandDto;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.command.replay.spi.ReplayCommandExecutionController;

public class ReplayableCommandExecution
        extends CommandExecutionAbstract {

    private final static Logger LOG =
            LoggerFactory.getLogger(ReplayableCommandExecution.class);

    private final SlaveConfiguration slaveConfig;

    public ReplayableCommandExecution(final SlaveConfiguration slaveConfig) {
        super(CommandExecutorService.SudoPolicy.SWITCH);
        this.slaveConfig = slaveConfig;
    }

    @Override
    protected void doExecute(final Object context) {
        Holder<SlaveStatus> holder = (Holder<SlaveStatus>) context;
        try {
            replicateAndRunCommands();
        } catch (StatusException e) {
            holder.setObject(e.slaveStatus);
        }
    }

    private void replicateAndRunCommands() throws  StatusException  {

        final PersistenceSession persistenceSession = getPersistenceSession();
        final IsisTransactionManager transactionManager = getTransactionManager(persistenceSession);

        CommandJdo hwmCommand = null;
        if(!isRunning()) {
            LOG.debug("ReplayableCommandExecution is paused");
            return;
        }

        while(isRunning()) {

            if(hwmCommand == null) {
                // first time through the loop we need to find the HWM command
                // (subsequent iterations we use the command from before as the HWM)
                LOG.debug("searching for hwm on slave ...");
                hwmCommand = commandServiceJdoRepository.findReplayHwm();
            }

            if(hwmCommand == null) {
                LOG.debug("could not find HWM on slave, breaking");
                return;

            }

            LOG.debug("current hwm transactionId = {} {} {} {}",
                    hwmCommand.getTransactionId(), hwmCommand.getTimestamp(),
                    hwmCommand.getExecuteIn(), hwmCommand.getMemberIdentifier());


            boolean fetchNext;
            switch (hwmCommand.getExecuteIn()) {
            case FOREGROUND:
                fetchNext = true;
                break;
            case REPLAYABLE:
                if(hwmCommand.getReplayState() == null) {

                    // the HWM has not been replayed.
                    // this might be because it has been marked for retry by the administrator.
                    // so, we will just use it directly

                    fetchNext = false;
                } else {
                    //
                    // check that the current HWM was replayed successfully, otherwise break out
                    //
                    if(hwmCommand.getReplayState().isFailed()) {
                        LOG.info("Command xactnId={} hit replay error", hwmCommand.getTransactionId());
                        return;
                    }
                    fetchNext = true;
                }
                break;
            case BACKGROUND:
            default:
                LOG.error(
                        "HWM command xactnId={} should be either FOREGROUND or REPLAYABLE but is instead {}; aborting",
                        hwmCommand.getTransactionId(), hwmCommand.getExecuteIn());
                return;
            }

            if(fetchNext) {
                //
                // replicate next command from master (if any)
                //
                final CommandDto commandDto = commandFetcher.fetchCommand(hwmCommand, slaveConfig);
                if (commandDto == null) {
                    LOG.info("No more commands found, breaking out");
                    return;
                }

                hwmCommand = transactionManager.executeWithinTransaction(
                        () -> commandServiceJdoRepository.saveForReplay(commandDto));
            }

            LOG.info("next HWM transactionId = {} {} {} {}", hwmCommand.getTransactionId());



            //
            // run command
            //
            execute(transactionManager, hwmCommand);


            //
            // find background commands, and run them
            //
            final CommandJdo parent = hwmCommand;
            final List<CommandJdo> backgroundCommands =
                    transactionManager.executeWithinTransaction(
                            () -> commandServiceJdoRepository.findBackgroundCommandsByParent(parent));
            for (final CommandJdo backgroundCommand : backgroundCommands) {
                execute(transactionManager, backgroundCommand);
            }


            //
            // if hit an issue, then mark this as in error.
            // this will effectively block the running of any further commands until the adminstrator fixes
            //
            transactionManager.executeWithinTransaction(() -> analysisService.analyze(parent));
        }
    }

    private boolean isRunning() {

        // if no controller implementation provided, then just continue
        if (controller == null) {
            return true;
        }

        final IsisTransactionManager transactionManager =
                getTransactionManager(getPersistenceSession());

        final ReplayCommandExecutionController.State state =
                transactionManager.executeWithinTransaction(() -> controller.getState());

        // if null, then not yet initialized, so fail back to not running
        if(state == null) {
            return false;
        }

        return state == ReplayCommandExecutionController.State.RUNNING;
    }

    @Inject
    CommandFetcher commandFetcher;

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

    @Inject
    CommandReplayAnalysisService analysisService;

    @Inject
    ReplayCommandExecutionController controller;
}