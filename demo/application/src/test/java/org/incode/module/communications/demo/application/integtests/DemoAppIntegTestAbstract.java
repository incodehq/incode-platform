/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package org.incode.module.communications.demo.application.integtests;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.collect.Maps;

import org.junit.BeforeClass;

import org.apache.isis.applib.services.xactn.TransactionService;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;
import org.apache.isis.core.runtime.authentication.standard.SimpleSession;

import org.isisaddons.module.command.dom.BackgroundCommandExecutionFromBackgroundCommandServiceJdo;
import org.isisaddons.module.command.dom.BackgroundCommandServiceJdoRepository;
import org.isisaddons.module.command.dom.CommandJdo;

import org.incode.module.communications.demo.application.manifest.DemoAppAppManifest;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class DemoAppIntegTestAbstract extends IntegrationTestAbstract {

    @BeforeClass
    public static void initSystem() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new DemoAppAppManifest() {
                        @Override
                        public Map<String, String> getConfigurationProperties() {
                            final Map<String, String> map = Maps.newHashMap();
                            Util.withJavaxJdoRunInMemoryProperties(map);
                            Util.withDataNucleusProperties(map);
                            Util.withIsisIntegTestProperties(map);
                            return map;
                        }
                    })
                    .build();
            isft.setUpSystem();
            IsisSystemForTest.set(isft);
        }

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }


    protected void runBackgroundCommands() throws InterruptedException {

        List<CommandJdo> commands = backgroundCommandRepository.findBackgroundCommandsNotYetStarted();
        assertThat(commands).hasSize(1);

        transactionService.nextTransaction();

        BackgroundCommandExecutionFromBackgroundCommandServiceJdo backgroundExec =
                new BackgroundCommandExecutionFromBackgroundCommandServiceJdo();
        final SimpleSession session = new SimpleSession("scheduler_user", new String[] { "admin_role" });

        final Thread thread = new Thread(() -> backgroundExec.execute(session, null));
        thread.start();

        thread.join(5000L);

        commands = backgroundCommandRepository.findBackgroundCommandsNotYetStarted();
        assertThat(commands).isEmpty();
    }

    @Inject
    protected BackgroundCommandServiceJdoRepository backgroundCommandRepository;

    @Inject
    protected TransactionService transactionService;

}
