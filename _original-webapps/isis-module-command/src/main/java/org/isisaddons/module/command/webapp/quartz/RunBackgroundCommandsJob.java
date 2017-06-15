package org.isisaddons.module.command.webapp.quartz;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.runtime.authentication.standard.SimpleSession;

import org.isisaddons.module.command.dom.BackgroundCommandExecutionFromBackgroundCommandServiceJdo;

public class RunBackgroundCommandsJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(RunBackgroundCommandsJob.class);

    public void execute(final JobExecutionContext context) throws JobExecutionException {

        final BackgroundCommandExecutionFromBackgroundCommandServiceJdo exec = new BackgroundCommandExecutionFromBackgroundCommandServiceJdo();

        final AuthenticationSession authSession = newAuthSession(context);

        LOG.debug("Running background commands");
        exec.execute(authSession, null);

    }

    protected String getKey(JobExecutionContext context, String key) {
        return context.getMergedJobDataMap().getString(key);
    }

    AuthenticationSession newAuthSession(JobExecutionContext context) {
        String user = getKey(context, "user");
        String rolesStr = getKey(context, "roles");
        String[] roles = Iterables.toArray(Splitter.on(",").split(rolesStr), String.class);
        return new SimpleSession(user, roles);
    }

}
