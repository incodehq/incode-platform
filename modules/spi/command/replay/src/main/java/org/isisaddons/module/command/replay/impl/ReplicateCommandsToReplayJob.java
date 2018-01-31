package org.isisaddons.module.command.replay.impl;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.services.configinternal.ConfigurationServiceInternal;
import org.apache.isis.core.runtime.authentication.standard.SimpleSession;
import org.apache.isis.core.runtime.sessiontemplate.AbstractIsisSessionTemplate;
import org.apache.isis.schema.cmd.v1.CommandsDto;

import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandReplayOnSlaveService;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;

import org.incode.module.jaxrsclient.dom.JaxRsClient;
import org.incode.module.jaxrsclient.dom.JaxRsResponse;

import lombok.Data;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ReplicateCommandsToReplayJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(ReplicateCommandsToReplayJob.class);

    enum Mode {
        OK,
        MISSING_ISIS_CONFIGURATION,
        REST_CALL_FAILING,
        FAILED_TO_UNMARSHALL_RESPONSE,
    }

    public void execute(final JobExecutionContext quartzContext) {

        final Mode mode = getMode(quartzContext);
        switch (mode) {
        case OK:
            // continue on...
            break;
        default:
            LOG.debug("skipped - {}" ,mode);
            return;
        }

        // create a session to interact with Isis runtime
        String slaveUser = getStringValue(quartzContext, Constants.SLAVE_USER_QUARTZ_KEY, Constants.SLAVE_USER_DEFAULT);
        String slaveRolesStr = getStringValue(quartzContext, Constants.SLAVE_ROLES_QUARTZ_KEY, Constants.SLAVE_ROLES_DEFAULT);
        String[] slaveRoles = Iterables.toArray(Splitter.on(",").split(slaveRolesStr), String.class);
        final AuthenticationSession authSession = new SimpleSession(slaveUser, slaveRoles);

        // obtain the isis configuration
        Map<String, String> isisConfigAsMap = lookupIsisConfigurationAsMap(authSession);

        // lookup remaining configuration properties from isis
        String masterUser = getStringValueElseNull(isisConfigAsMap, Constants.MASTER_USER_ISIS_KEY);
        String masterPassword = getStringValueElseNull(isisConfigAsMap, Constants.MASTER_PASSWORD_ISIS_KEY);
        String masterBaseUrl = getStringValueElseNull(isisConfigAsMap, Constants.MASTER_BASE_URL_ISIS_KEY);

        if(masterUser == null || masterPassword == null || masterBaseUrl == null) {
            // issue will already have been logged
            setMode(quartzContext, Mode.MISSING_ISIS_CONFIGURATION);
            return;
        }
        if(!masterBaseUrl.endsWith("/")) {
            masterBaseUrl = masterBaseUrl + "/";
        }

        final int maxNumberBatches =
                getIntValue(isisConfigAsMap, Constants.SLAVE_MAX_NUMBER_BATCHES_ISIS_KEY, Constants.SLAVE_MAX_NUMBER_BATCHES_DEFAULT);
        final int batchSize =
                getIntValue(isisConfigAsMap, Constants.SLAVE_BATCH_SIZE_ISIS_KEY, Constants.SLAVE_BATCH_SIZE_DEFAULT);

        final JaxbService jaxbService = new JaxbService.Simple();

        // we keep going until there are no more commands to replicate, or until the maximum number of batches reached
        for (int batchNum = 1; batchNum <= maxNumberBatches; batchNum++) {


            //
            // find hwm transaction on slave
            //
            LOG.debug("batch {}: searching for hwm on slave ...", batchNum);
            final UUID transactionId = findHwmTransactionIdIfAny(authSession);
            LOG.debug("batch {}: hwm transactionId = {}", batchNum, transactionId);




            //
            // find commands on master
            //
            LOG.debug("batch {}: finding commands on master ...", batchNum);
            final UriBuilder uriBuilder = UriBuilder.fromUri(
                    transactionId != null
                        ? String.format(
                                "%s%s?transactionId=%s&batchSize=%d",
                                masterBaseUrl, Constants.URL_SUFFIX, transactionId, batchSize)
                        : String.format(
                                "%s%s?batchSize=%d",
                                masterBaseUrl, Constants.URL_SUFFIX, batchSize)
            );
            final URI uri = uriBuilder.build();
            LOG.debug("batch {}: uri = {}", batchNum, uri);

            final JaxRsResponse response;
            final JaxRsClient jaxRsClient = new JaxRsClient.Default();
            try {
                response = jaxRsClient.get(uri, CommandsDto.class, JaxRsClient.ReprType.ACTION_RESULT, masterUser, masterPassword);
                int status = response.getStatus();
                if(status != Response.Status.OK.getStatusCode()) {
                    final String entity = readEntityFrom(response);
                    if(entity != null) {
                        LOG.warn("batch {}: status: {}, entity: \n{}", batchNum, status, entity);
                    } else {
                        LOG.warn("batch {}: status: {}, unable to read entity from response", batchNum, status);
                    }
                    setMode(quartzContext, Mode.REST_CALL_FAILING);
                    return;
                }
            } catch(Exception ex) {
                LOG.warn("batch {}: rest call failed", batchNum, ex);
                setMode(quartzContext, Mode.REST_CALL_FAILING);
                return;
            }

            CommandsDto commandsDto;
            String entity = "<unable to read from response entity>";
            try {
                entity = readEntityFrom(response);
                commandsDto = jaxbService.fromXml(CommandsDto.class, entity);
                LOG.debug("batch {}: commands:\n{}", batchNum, entity);
            } catch(Exception ex) {
                LOG.warn("batch {}: unable to unmarshal entity to CommandsDto.class; was:\n{}", batchNum, entity);
                setMode(quartzContext, Mode.FAILED_TO_UNMARSHALL_RESPONSE);
                return;
            }


            final int size = commandsDto.getCommandDto().size();
            if(size == 0) {
                // here's where we exit out of the loop.
                LOG.debug("batch {}: no commands found for replay, this job execution is complete", batchNum);
                return;
            }



            //
            // save commands
            //
            LOG.debug("batch {}: saving {} commands for replay ...", batchNum, size);

            new AbstractIsisSessionTemplate() {
                @Override
                protected void doExecuteWithTransaction(final Object dtoAsObj) {
                    CommandsDto dto = (CommandsDto) dtoAsObj;
                    commandServiceJdoRepository.saveForReplay(dto);
                }

                @Inject
                CommandServiceJdoRepository commandServiceJdoRepository;
            }.execute(authSession, commandsDto);
        }
    }

    private static String readEntityFrom(final JaxRsResponse response) {
        try {
            return response.readEntity(String.class);
        } catch(Exception e) {
            return null;
        }
    }

    @Data class Holder<T> { T object; }

    private Map<String,String> lookupIsisConfigurationAsMap(final AuthenticationSession authSession) {

        final Holder<Map<String,String>> holder = new Holder<>();
        new AbstractIsisSessionTemplate() {
            @Override
            protected void doExecuteWithTransaction(final Object holderAsObj) {
                Holder<Map<String, String>> holder = (Holder<Map<String, String>>) holderAsObj;
                Map<String, String> map = configurationServiceInternal.asMap();
                holder.setObject(map);
            }

            @Inject
            ConfigurationServiceInternal configurationServiceInternal;
        }.execute(authSession, holder);

        return holder.getObject();
    }

    private UUID findHwmTransactionIdIfAny(final AuthenticationSession authSession) {
        CommandJdo hwmCommand = findHwmCommandIfAny(authSession);
        return hwmCommand != null ? hwmCommand.getTransactionId() : null;
    }

    private CommandJdo findHwmCommandIfAny(final AuthenticationSession authSession) {

        final Holder<CommandJdo> holder = new Holder<>();
        new AbstractIsisSessionTemplate() {
            @Override
            protected void doExecuteWithTransaction(final Object holderAsObj) {
                Holder<CommandJdo> holder = (Holder<CommandJdo>) holderAsObj;
                holder.setObject(slaveService.findReplayHwmOnSlave());
            }

            @Inject
            CommandReplayOnSlaveService slaveService;
        }.execute(authSession, holder);

        return holder.getObject();
    }

    //region > quartz configuration; getMode/setMode

    private Mode getMode(final JobExecutionContext context) {
        String mode = getStringValue(context, "mode", Mode.OK.name());
        return Mode.valueOf(mode);
    }

    private void setMode(final JobExecutionContext context, final Mode mode) {
        setString(context, "mode", mode.name());
    }

    /**
     * Lookup from quartz configuration for this job.
     */
    private static String getStringValue(JobExecutionContext context, String key, final String defaultValue) {
        try {
            String v = context.getJobDetail().getJobDataMap().getString(key);
            return v != null ? v : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }
    /**
     * Save into quartz configuration for this job, for next invocation.
     */
    private static void setString(JobExecutionContext context, String key, String value) {
        context.getJobDetail().getJobDataMap().put(key, value);
    }

    //endregion

    //region > isis configuration


    /**
     * Lookup from Isis configuration (reifined into a string/string map)
     */
    private static int getIntValue(
            final Map<String, String> map, final String key, final int defaultValue) {
        try {
            return Integer.parseInt(map.get(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Lookup from Isis configuration (reified into a string/string map)
     */
    private static String getStringValueElseNull(
            final Map<String, String> map, final String key) {
        String value = map.get(key);
        if(value == null) {
            LOG.warn("Missing Isis configuration property '{}'", key);
        }
        return value;
    }

    //endregion

}

