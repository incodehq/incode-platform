/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.module.settings.fixture.scripts;

import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import org.joda.time.LocalDate;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class SettingsModuleAppSetUpFixture extends DiscoverableFixtureScript {

    public SettingsModuleAppSetUpFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new SettingsModuleAppTearDownFixture(), executionContext);

        // create

        executionContext.add(this, applicationSettingsServiceRW.newBoolean("demoSettingEnableJmx", "(Demo) Whether JMX beans are enabled", true));
        executionContext.add(this, applicationSettingsServiceRW.newInt("demoSettingWebServiceRetries", "(Demo) # times to retry submitting web service retries before failing", 3));
        executionContext.add(this, applicationSettingsServiceRW.newLocalDate("demoSettingEpochDate", "(Demo) Earliest data available in the app (migration date)", new LocalDate(2010, 1, 1)));
        executionContext.add(this, applicationSettingsServiceRW.newLong("demoSettingInitialOrderNumber", "(Demo) Number from which to start generating order numbers ", 100000000000L));
        executionContext.add(this, applicationSettingsServiceRW.newString("demoSettingBaseUrl", "(Demo) base URL for Restful access", "http://isisapp.mycompany.com"));

        executionContext.add(this, userSettingsServiceRW.newBoolean("sven", "demoSettingNotifyByEmail", "(Demo) whether to notify changes by email", true));
        executionContext.add(this, userSettingsServiceRW.newInt("sven", "demoSettingPlaceOrderDefaultQuantity", "(Demo) Default quantity when placing an order'", 1));
        executionContext.add(this, userSettingsServiceRW.newLocalDate("sven", "demoSettingLastLoggedOn", "(Demo) the last date that this user logged on", new LocalDate(2014,9,18)));
        executionContext.add(this, userSettingsServiceRW.newLong("sven", "demoSettingThemeRgba", "(Demo) theme, as RGBA format'", 128064254040L));
        executionContext.add(this, userSettingsServiceRW.newString("sven", "demoSettingMostRecentChatMessageSent", "(Demo) most recent chat message sent", "Grab some lunch?"));

        executionContext.add(this, userSettingsServiceRW.newBoolean("dick", "demoSettingNotifyByEmail", "(Demo) whether to notify changes by email", false));

    }

    @javax.inject.Inject
    private ApplicationSettingsServiceRW applicationSettingsServiceRW;

    @javax.inject.Inject
    private UserSettingsServiceRW userSettingsServiceRW;

}
