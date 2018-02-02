package org.isisaddons.module.quartz.dom.jobs;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.runtime.authentication.standard.SimpleSession;

import org.isisaddons.module.command.dom.BackgroundCommandExecutionFromBackgroundCommandServiceJdo;

public class RunBackgroundCommandsJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(RunBackgroundCommandsJob.class);

    public void execute(final JobExecutionContext context) {

        final AuthenticationSession authSession = newAuthSession(context);

        LOG.debug("Running background commands");
        new BackgroundCommandExecutionFromBackgroundCommandServiceJdo().execute(authSession, null);
    }

    protected String getKey(JobExecutionContext context, String key) {
        return context.getMergedJobDataMap().getString(key);
    }

    protected AuthenticationSession newAuthSession(JobExecutionContext context) {
        String user = getKey(context, "user");
        String rolesStr = getKey(context, "roles");
        String[] roles = Iterables.toArray(Splitter.on(",").split(rolesStr), String.class);
        return new SimpleSession(user, roles);
    }


}
