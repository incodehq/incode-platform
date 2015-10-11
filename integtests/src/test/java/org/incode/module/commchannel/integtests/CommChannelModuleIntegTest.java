/*
 *  Copyright 2014~2015 Dan Haywood
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
package org.incode.module.commchannel.integtests;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import org.apache.log4j.Level;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.commchannel.app.CommChannelModuleAppManifest;
import org.incode.module.commchannel.dom.api.geocoding.GeocodingService;

public abstract class CommChannelModuleIntegTest extends IntegrationTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    protected FixtureScripts fixtureScripts;

    @Inject
    protected FakeDataService fakeDataService;

    protected static <T> List<T> asList(final Iterable<T> iterable) {
        return Lists.newArrayList(iterable);
    }

    @BeforeClass
    public static void initClass() {
        org.apache.log4j.PropertyConfigurator.configure("logging.properties");
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(Level.INFO)
                    .with(new CommChannelModuleAppManifest() {
                                @Override
                                public Map<String, String> getConfigurationProperties() {
                                    return ImmutableMap.of(GeocodingService.class.getCanonicalName() + ".demo", "true");
                                }
                            }
                            .withModules(CommChannelModuleIntegTest.class, FakeDataModule.class)
                    )
                    .with(new IsisConfigurationForJdoIntegTests())
                    .build();
            isft.setUpSystem();
            IsisSystemForTest.set(isft);
        }

        // instantiating will install onto ThreadLocal
        new ScenarioExecutionForIntegration();
    }

}
