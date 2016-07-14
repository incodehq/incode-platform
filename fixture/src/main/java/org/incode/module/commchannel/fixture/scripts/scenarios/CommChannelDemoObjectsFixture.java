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
package org.incode.module.commchannel.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.commchannel.dom.impl.emailaddress.Object_newEmailAddress;
import org.incode.module.commchannel.dom.impl.phoneorfax.Object_newPhoneOrFaxNumber;
import org.incode.module.commchannel.dom.impl.postaladdress.Object_newPostalAddress;
import org.incode.module.commchannel.dom.impl.type.CommunicationChannelType;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;

public class CommChannelDemoObjectsFixture extends DiscoverableFixtureScript {

    public CommChannelDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
	executionContext.executeChild(this, new CommChannelDemoObjectsTearDownFixture());

        final CommChannelDemoObject demoOwner = create("Foo", executionContext);

        wrap(newEmailAddress(demoOwner)).$$("foo@example.com", "Other Email", null);
        wrap(newPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.PHONE_NUMBER, "555 1234", "Home Number", null);
        wrap(newPhoneOrFaxNumber(demoOwner)).$$(CommunicationChannelType.FAX_NUMBER, "555 4321", "Work Fax", null);

        final CommChannelDemoObject bar = create("Bar", executionContext);
        wrap(newPostalAddress(demoOwner)).$$(
                "45", "High Street", "Oxford", null, null, "UK", "Shipping Address", null, false);

        final CommChannelDemoObject baz = create("Baz", executionContext);
    }

    Object_newEmailAddress newEmailAddress(final CommChannelDemoObject demoOwner) {
        return container.mixin(Object_newEmailAddress.class, demoOwner);
    }

    Object_newPhoneOrFaxNumber newPhoneOrFaxNumber(final CommChannelDemoObject demoOwner) {
        return container.mixin(Object_newPhoneOrFaxNumber.class, demoOwner);
    }

    Object_newPostalAddress newPostalAddress(final CommChannelDemoObject demoOwner) {
        return container.mixin(Object_newPostalAddress.class, demoOwner);
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
    ClockService clockService;

}
