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
package org.isisaddons.module.commchannel.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.isisaddons.module.commchannel.dom.CommunicationChannelContributions;
import org.isisaddons.module.commchannel.dom.CommunicationChannelType;
import org.isisaddons.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.isisaddons.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.isisaddons.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;

public class CommChannelDemoObjectsFixture extends DiscoverableFixtureScript {

    public CommChannelDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
	executionContext.executeChild(this, new CommChannelDemoObjectsTearDownFixture());

        final CommChannelDemoObject foo = create("Foo", executionContext);

        wrap(communicationChannelContributions).newEmail(foo, CommunicationChannelType.EMAIL_ADDRESS, "foo@example.com");
        wrap(communicationChannelContributions).newPhoneOrFax(foo, CommunicationChannelType.PHONE_NUMBER, "555 1234");
        wrap(communicationChannelContributions).newPhoneOrFax(foo, CommunicationChannelType.FAX_NUMBER, "555 4321");

        final CommChannelDemoObject bar = create("Bar", executionContext);
        wrap(communicationChannelContributions).newPostal(bar, CommunicationChannelType.POSTAL_ADDRESS, "23 High Street", null, null, "Oxford", "Oxon", "OX1 1AA", "UK");

        final CommChannelDemoObject baz = create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private CommChannelDemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(commChannelDemoObjectMenu).create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @javax.inject.Inject
    CommunicationChannelContributions communicationChannelContributions;
    @javax.inject.Inject
    ClockService clockService;

}
