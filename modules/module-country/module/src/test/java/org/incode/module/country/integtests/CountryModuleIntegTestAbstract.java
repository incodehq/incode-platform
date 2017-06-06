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
package org.incode.module.country.integtests;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.junit.Before;
import org.junit.BeforeClass;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

import org.incode.module.country.CountryModuleManifest;
import org.incode.module.country.fixture.teardown.CountryModuleTearDown;
import org.incode.module.integtestsupport.dom.IncodeIntegrationTestAbstract;

public abstract class CountryModuleIntegTestAbstract extends IncodeIntegrationTestAbstract {

    @Before
    public void cleanUpFromPreviousTest() {
        runFixtureScript(new CountryModuleTearDown());
    }

    @BeforeClass
    public static void initSystem() {
        org.apache.log4j.PropertyConfigurator.configure("logging-integtest.properties");
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new CountryModuleManifest() {
                        @Override
                        public Map<String, String> getConfigurationProperties() {
                            final Map<String, String> map = Maps.newHashMap();
                            AppManifest.Util.withJavaxJdoRunInMemoryProperties(map);
                            AppManifest.Util.withDataNucleusProperties(map);
                            AppManifest.Util.withIsisIntegTestProperties(map);
                            return map;
                        }

                        @Override public List<Class<?>> getAdditionalServices() {
                            return Lists.newArrayList(ModuleFixtureScriptsSpecificationProvider.class);
                        }
                    })

                    .build();
            isft.setUpSystem();
            IsisSystemForTest.set(isft);
        }

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

    @DomainService(nature = NatureOfService.DOMAIN)
    public static class ModuleFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
        @Override
        public FixtureScriptsSpecification getSpecification() {
            return FixtureScriptsSpecification.builder("org.incode.module.country").with(
                    FixtureScripts.MultipleExecutionStrategy.EXECUTE_ONCE_BY_VALUE).build();
        }
    }
}
