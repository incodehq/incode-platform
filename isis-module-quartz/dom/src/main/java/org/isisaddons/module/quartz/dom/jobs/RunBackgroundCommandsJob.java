/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.quartz.dom.jobs;

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
