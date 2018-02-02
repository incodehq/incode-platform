package org.isisaddons.module.command.replay.impl;

import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.services.configinternal.ConfigurationServiceInternal;
import org.apache.isis.core.runtime.authentication.standard.SimpleSession;
import org.apache.isis.core.runtime.sessiontemplate.AbstractIsisSessionTemplate;

import org.isisaddons.module.command.dom.BackgroundCommandExecutionFromBackgroundCommandServiceJdo;

import static org.isisaddons.module.command.replay.impl.Constants.SLAVE_ROLES_DEFAULT;
import static org.isisaddons.module.command.replay.impl.Constants.SLAVE_ROLES_QUARTZ_KEY;
import static org.isisaddons.module.command.replay.impl.Constants.SLAVE_USER_DEFAULT;
import static org.isisaddons.module.command.replay.impl.Constants.SLAVE_USER_QUARTZ_KEY;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class RunBackgroundCommandsWithReplicationAndReplayJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(RunBackgroundCommandsWithReplicationAndReplayJob.class);

    AuthenticationSession authSession;
    SlaveConfiguration slaveConfig;

    public void execute(final JobExecutionContext quartzContext) {

        // figure out if this instance is configured to run as master or slave
        authSession = new SimpleSessionFromQuartz(quartzContext);
        final Map<String, String> isisConfigAsMap = lookupIsisConfigurationAsMap(authSession);
        slaveConfig = new SlaveConfiguration(isisConfigAsMap);

        if(!slaveConfig.isConfigured()) {
            runBackgroundCommandsOnMaster();
        } else {
            runBackgroundCommandsOnSlave(quartzContext);
        }
    }

    private void runBackgroundCommandsOnMaster() {
        // same as the original RunBackgroundCommandsJob
        new BackgroundCommandExecutionFromBackgroundCommandServiceJdo().execute(authSession, null);
    }

    private void runBackgroundCommandsOnSlave(final JobExecutionContext quartzContext) {
        final SlaveStatus slaveStatus = 
                getSlaveStatus(quartzContext, SlaveStatus.TICKING_CLOCK_STATUS_UNKNOWN);

        switch (slaveStatus) {

            case TICKING_CLOCK_STATUS_UNKNOWN:
            case TICKING_CLOCK_NOT_YET_INITIALIZED:
                final boolean initialized = lookupTickingClockServiceStatus(authSession);
                if(initialized) {
                    setSlaveStatus(quartzContext, SlaveStatus.OK);
                    // go round the loop
                    runBackgroundCommandsOnMaster();
                } else {
                    setSlaveStatus(quartzContext, SlaveStatus.TICKING_CLOCK_NOT_YET_INITIALIZED);
                }
                return;

            case OK:
                Holder<SlaveStatus> holder = new Holder<>();
                new ReplayableCommandExecution(slaveConfig).execute(authSession, holder);
                final SlaveStatus newStatus = holder.getObject();
                if(newStatus != null) {
                    setSlaveStatus(quartzContext, newStatus);
                }
                return;


            case REST_CALL_FAILING:
            case FAILED_TO_UNMARSHALL_RESPONSE:
            case UNKNOWN_STATE:
                LOG.warn("skipped - configured as slave, however: {}" ,slaveStatus);
                return;
            default:
                throw new IllegalStateException("Unrecognised status: " + slaveStatus);
        }

    }

    private Map<String,String> lookupIsisConfigurationAsMap(final AuthenticationSession authSession) {

        final Holder<Map<String,String>> holder = new Holder<>();
        new AbstractIsisSessionTemplate() {
            @Override
            protected void doExecuteWithTransaction(final Object unused) {
                Map<String, String> map = configurationServiceInternal.asMap();
                holder.setObject(map);
            }

            @Inject
            ConfigurationServiceInternal configurationServiceInternal;
        }.execute(authSession, null);

        return holder.getObject();
    }

    private boolean lookupTickingClockServiceStatus(final AuthenticationSession authSession) {

        final Holder<Boolean> holder = new Holder<>();
        new AbstractIsisSessionTemplate() {
            @Override
            protected void doExecuteWithTransaction(final Object unused) {
                holder.setObject(tickingClockService.isInitialized());
            }

            @Inject
            TickingClockService tickingClockService;
        }.execute(authSession, null);

        return holder.getObject();
    }

    static class SimpleSessionFromQuartz extends SimpleSession {

        private static String getUser(final JobExecutionContext quartzContext) {
            return getString(quartzContext, SLAVE_USER_QUARTZ_KEY, SLAVE_USER_DEFAULT);
        }

        private static String[] getRoles(final JobExecutionContext quartzContext) {
            return Iterables.toArray(Splitter.on(",").split(
                    getString(quartzContext, SLAVE_ROLES_QUARTZ_KEY, SLAVE_ROLES_DEFAULT)),
                    String.class);
        }

        SimpleSessionFromQuartz(final JobExecutionContext quartzContext) {
            super(getUser(quartzContext), getRoles(quartzContext));
        }
    }

    //region > quartz configuration; getSlaveStatus/setSlaveStatus

    private static final String KEY_SLAVE_STATUS = "slaveStatus";

    /**
     * Lookup from quartz configuration for this job.
     */
    private static String getString(JobExecutionContext context, String key, final String defaultValue) {
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

    private SlaveStatus getSlaveStatus(
            final JobExecutionContext context,
            final SlaveStatus defaultStatus) {
        String mode = getString(context, KEY_SLAVE_STATUS, defaultStatus.name());
        return SlaveStatus.valueOf(mode);
    }

    private void setSlaveStatus(final JobExecutionContext context, final SlaveStatus mode) {
        setString(context, KEY_SLAVE_STATUS, mode.name());
    }

    //endregion

}

