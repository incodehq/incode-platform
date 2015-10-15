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
import org.incode.module.commchannel.dom.api.geocoding.GeocodingService;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannelOwner_communicationChannels;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_remove;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_updateDescription;
import org.incode.module.commchannel.dom.impl.channel.CommunicationChannel_updateNotes;
import org.incode.module.commchannel.dom.impl.emailaddress.CommunicationChannelOwner_newEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.CommunicationChannelOwner_newPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.CommunicationChannelOwner_newPostalAddress;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;

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

    protected CommunicationChannelOwner_newEmailAddress mixinNewEmailAddress(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwner_newEmailAddress.class, owner);
    }

    protected CommunicationChannelOwner_newPostalAddress mixinNewPostalAddress(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwner_newPostalAddress.class, owner);
    }

    protected CommunicationChannelOwner_newPhoneOrFaxNumber mixinNewPhoneOrFaxNumber(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwner_newPhoneOrFaxNumber.class, owner);
    }

    protected CommunicationChannel_remove mixinRemove(final CommunicationChannel channel) {
        return mixin(CommunicationChannel_remove.class, channel);
    }

    protected CommunicationChannel_updateDescription mixinUpdateDescription(final CommunicationChannel communicationChannel) {
        return mixin(CommunicationChannel_updateDescription.class, communicationChannel);
    }

    protected CommunicationChannel_updateNotes mixinUpdateNotes(final CommunicationChannel communicationChannel) {
        return mixin(CommunicationChannel_updateNotes.class, communicationChannel);
    }

    protected CommunicationChannelOwner_communicationChannels mixinCommunicationChannels(final CommChannelDemoObject owner) {
        return mixin(CommunicationChannelOwner_communicationChannels.class, owner);
    }


}
