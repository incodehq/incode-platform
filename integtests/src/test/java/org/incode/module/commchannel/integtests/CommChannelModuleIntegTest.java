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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;

import org.apache.log4j.Level;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.commchannel.app.CommChannelModuleAppManifest;
import org.incode.module.commchannel.dom.api.GeocodingService;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.T_communicationChannels;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_remove1;
import org.incode.module.commchannel.dom.impl.emailaddress.T_addEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.T_addPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.T_addPostalAddress;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommunicationChannelOwnerLinkForDemoObject;

public abstract class CommChannelModuleIntegTest extends IntegrationTestAbstract {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Inject
    protected DomainObjectContainer container;

    @Inject
    protected FixtureScripts fixtureScripts;

    @Inject
    protected FakeDataService fakeDataService;

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

    protected T_addEmailAddress mixinNewEmailAddress(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addEmailAddress.class, owner);
    }

    protected T_addPostalAddress mixinNewPostalAddress(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addPostalAddress.class, owner);
    }

    protected T_addPhoneOrFaxNumber mixinNewPhoneOrFaxNumber(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._addPhoneOrFaxNumber.class, owner);
    }

    protected CommunicationChannel_remove1 mixinRemove(final CommunicationChannel channel) {
        return mixin(CommunicationChannel_remove1.class, channel);
    }

    protected T_communicationChannels mixinCommunicationChannels(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwnerLinkForDemoObject._communicationChannels.class, owner);
    }

    /**
     * Tries to retrieve some content, 1 second timeout.
     */
    protected static boolean isInternetReachable()
    {
        try {
            final URL url = new URL("http://www.google.com");
            final HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setConnectTimeout(1000);
            urlConnect.getContent();
            urlConnect.disconnect();
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
