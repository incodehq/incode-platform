package org.isisaddons.module.command.replay.impl;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.common.base.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.services.command.Command;
import org.apache.isis.applib.services.command.CommandWithDto;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.core.runtime.services.background.CommandExecutionAbstract;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;
import org.apache.isis.core.runtime.system.transaction.IsisTransactionManager;
import org.apache.isis.schema.cmd.v1.CommandDto;
import org.apache.isis.schema.cmd.v1.CommandsDto;
import org.apache.isis.schema.utils.CommandDtoUtils;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandReplayOnSlaveService;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.command.dom.ReplayState;

import org.incode.module.jaxrsclient.dom.JaxRsClient;
import org.incode.module.jaxrsclient.dom.JaxRsResponse;

import static org.isisaddons.module.command.replay.impl.Constants.URL_SUFFIX;

public class ReplayableCommandExecution
        extends CommandExecutionAbstract {

    private final static Logger LOG =
            LoggerFactory.getLogger(ReplayableCommandExecution.class);
    private final SlaveConfiguration slaveConfig;

    public ReplayableCommandExecution(final SlaveConfiguration slaveConfig) {
        super(SudoPolicy.SWITCH);
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

    private void replicateAndRunCommands() throws  StatusException {

        final PersistenceSession persistenceSession = getPersistenceSession();
        final IsisTransactionManager transactionManager = getTransactionManager(persistenceSession);

        CommandJdo commandJdo = null;
        while(true) {

            if(commandJdo == null) {
                // find HWM command, if any
                LOG.debug("searching for hwm on slave ...");
                commandJdo = slaveService.findReplayHwmOnSlave();
            }

            if(hitReplayError(commandJdo)) {
                LOG.info("Command xactnId={} hit replay error", commandJdo.getTransactionId());
                return;
            }

            // fetch next command
            final UUID transactionId = commandJdo != null ? commandJdo.getTransactionId() : null;
            final int batchSize = 1;
            LOG.debug("hwm transactionId = {}", transactionId);

            CommandsDto commandsDto = fetchCommandsFromMaster(transactionId, batchSize);
            if (commandsDto == null) {
                return;
            }

            // this is our new HWM, to replay
            commandJdo = saveCommandForReplay(commandsDto);
            if(commandJdo == null) {
                return;
            }

            // run command
            executeCommand(transactionManager, commandJdo);

            // find background commands, and run them
            final List<CommandJdo> backgroundCommands =
                    commandServiceJdoRepository.findBackgroundCommandsByParent(commandJdo);
            for (final CommandJdo backgroundCommand : backgroundCommands) {
                executeCommand(transactionManager, backgroundCommand);
            }

            // if hit an issue, then mark this as in error.
            // this will effectively block the running of any further commands until the adminstrator fixes
            commandJdo.setReplayState(isReplayedOk(commandJdo));
        }

    }

    private boolean hitReplayError(final CommandJdo commandJdo) {
        return  commandJdo != null &&
                commandJdo.getReplayState() != null &&
                commandJdo.getReplayState().representsError();
    }

    /**
     * @param commandJdo - to analyze
     * @return - whether this command is replayed and need to fetch another one
     * @throws StatusException - could not determine the status of the command
     */
    private ReplayState isReplayedOk(final CommandJdo commandJdo) {

        final CommandDto origCommandDto = commandJdo.asDto();

        // see if the number of child commands was the same
        final List<CommandJdo> childCommands =
                commandServiceJdoRepository.findBackgroundCommandsByParent(commandJdo);

        final String origCommandNumChildCommandsStr =
                CommandDtoUtils.getUserData(origCommandDto, CommandJdo.USERDATA_KEY_NUMBER_CHILD_COMMANDS);
        if(origCommandNumChildCommandsStr != null) {
            try {
                final int origCommandChildCommands = Integer.parseInt(origCommandNumChildCommandsStr);
                if (origCommandChildCommands != childCommands.size()) {
                    return ReplayState.NUM_CHILDREN_DIFFER;
                }
            } catch (NumberFormatException ex) {
                return ReplayState.UNABLE_TO_CHECK_CHILDREN;
            }
        }

        // see if the outcome was the same...
        // ... either the same result when replayed
        final String origResultStr =
                CommandDtoUtils.getUserData(origCommandDto, CommandWithDto.USERDATA_KEY_RETURN_VALUE);
        if(origResultStr != null) {
            final String replayedResultStr = commandJdo.getResultStr();
            return Objects.equal(origResultStr, replayedResultStr)
                    ? ReplayState.OK
                    : ReplayState.RESULT_DIFFERS;
        }

        // ... or the same exception when replayed
        final String origException =
                CommandDtoUtils.getUserData(origCommandDto, CommandWithDto.USERDATA_KEY_EXCEPTION);
        if(origException != null) {
            final String replayedException = commandJdo.getException();
            return Objects.equal(origException, replayedException)
                    ? ReplayState.OK
                    : ReplayState.EXCEPTION_DIFFERS;
        }


        // presumably a void action
        return ReplayState.OK;
    }

    static class StatusException extends Exception {
        final SlaveStatus slaveStatus;

        StatusException(SlaveStatus slaveStatus) {
            this(slaveStatus, null);
        }
        StatusException(SlaveStatus slaveStatus, final Exception ex) {
            super(ex);
            this.slaveStatus = slaveStatus;
        }
    }

    private CommandsDto fetchCommandsFromMaster(final UUID transactionId, final int batchSize)
            throws StatusException {
        //
        // find commands on master
        //
        LOG.debug("finding commands on master ...");
        final UriBuilder uriBuilder = UriBuilder.fromUri(
                transactionId != null
                        ? String.format(
                        "%s%s?transactionId=%s&batchSize=%d",
                        slaveConfig.masterBaseUrl, URL_SUFFIX, transactionId, batchSize)
                        : String.format(
                        "%s%s?batchSize=%d",
                        slaveConfig.masterBaseUrl, URL_SUFFIX, batchSize)
        );
        final URI uri = uriBuilder.build();
        LOG.debug("uri = {}", uri);

        final JaxRsResponse response;
        final JaxRsClient jaxRsClient = new JaxRsClient.Default();
        try {
            response = jaxRsClient.get(uri, CommandsDto.class, JaxRsClient.ReprType.ACTION_RESULT, slaveConfig.masterUser, slaveConfig.masterPassword);
            int status = response.getStatus();
            if(status != Response.Status.OK.getStatusCode()) {
                final String entity = readEntityFrom(response);
                if(entity != null) {
                    LOG.warn("status: {}, entity: \n{}", status, entity);
                } else {
                    LOG.warn("status: {}, unable to read entity from response", status);
                }
                throw new StatusException(SlaveStatus.REST_CALL_FAILING);
            }
        } catch(Exception ex) {
            LOG.warn("rest call failed", ex);
            throw new StatusException(SlaveStatus.REST_CALL_FAILING, ex);
        }

        CommandsDto commandsDto;
        String entity = "<unable to read from response entity>";
        try {
            entity = readEntityFrom(response);
            final JaxbService jaxbService = new JaxbService.Simple();
            commandsDto = jaxbService.fromXml(CommandsDto.class, entity);
            LOG.debug("commands:\n{}", entity);
        } catch(Exception ex) {
            LOG.warn("unable to unmarshal entity to CommandsDto.class; was:\n{}", entity);
            throw new StatusException(SlaveStatus.FAILED_TO_UNMARSHALL_RESPONSE, ex);
        }

        final int size = commandsDto.getCommandDto().size();
        if(size == 0) {
            // here's where we exit out of the loop.
            LOG.debug("no commands found for replay, this job execution is complete");
            return null;
        }
        return commandsDto;
    }

    private static String readEntityFrom(final JaxRsResponse response) {
        try {
            return response.readEntity(String.class);
        } catch(Exception e) {
            return null;
        }
    }

    private CommandJdo saveCommandForReplay(final CommandsDto commandsDto) {
        LOG.debug("saving command for replay ...");

        final List<CommandJdo> commandJdos =
                commandServiceJdoRepository.saveForReplay(commandsDto);
        return !commandJdos.isEmpty()
                ? commandJdos.get(0)
                : null;
    }

    @Override
    protected Exception executeCommand(
            final IsisTransactionManager transactionManager,
            final Command command) {

        return tickingClockService.at(command.getTimestamp(),
                () -> super.doExecuteCommand(transactionManager, command));
    }

    @javax.inject.Inject
    TickingClockService tickingClockService;

    @Inject
    CommandReplayOnSlaveService slaveService;

    @Inject
    CommandServiceJdoRepository commandServiceJdoRepository;

}