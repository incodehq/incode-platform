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
package org.incode.module.communications.demo.module.fixture.scenario.commchannel;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.communications.demo.module.dom.impl.commchannel.CommChannelDemoObject;
import org.incode.module.communications.demo.module.dom.impl.commchannel.CommChannelDemoObjectMenu;
import org.incode.module.communications.demo.module.dom.impl.commchannel.DemoCommunicationChannelOwner_newChannelContributions;
import org.incode.module.communications.demo.module.fixture.teardown.DemoModuleTearDown;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelType;
import org.incode.module.country.dom.impl.Country;
import org.incode.module.country.dom.impl.CountryRepository;
import org.incode.module.country.dom.impl.State;

public class CommChannelDemoObjectsFixture extends DiscoverableFixtureScript {

    public CommChannelDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

    // prereqs
	executionContext.executeChild(this, new DemoModuleTearDown());

        final CommChannelDemoObject demoOwner = create("Foo", executionContext);

        wrapAddEmailAddress(demoOwner, "foo@example.com");
        wrapAddPhoneOrFaxNumber(demoOwner, CommunicationChannelType.PHONE_NUMBER, "555 1234");
        wrapAddPhoneOrFaxNumber(demoOwner, CommunicationChannelType.FAX_NUMBER, "555 4321");

        final CommChannelDemoObject bar = create("Bar", executionContext);
        Country country = countryRepository.findCountry("UK");
        wrapAddPostalAddress(bar, country, null, "45", "High Street", "Oxford", null, null);

        final CommChannelDemoObject baz = create("Baz", executionContext);
    }

    void wrapAddEmailAddress(final CommunicationChannelOwner cco, final String address) {
        wrap(demoCommunicationChannelOwner_newChannelContributions).newEmail(cco, CommunicationChannelType.EMAIL_ADDRESS, address);
    }

    void wrapAddPhoneOrFaxNumber(
            final CommunicationChannelOwner cco,
            final CommunicationChannelType type,
            final String number) {
        wrap(demoCommunicationChannelOwner_newChannelContributions).newPhoneOrFax(cco, type, number);
    }

    void wrapAddPostalAddress(
            final CommunicationChannelOwner cco,
            final Country country,
            final State state,
            final String addressLine1,
            final String addressLine2,
            final String addressLine3,
            final String postalCode,
            final String city) {
        wrap(demoCommunicationChannelOwner_newChannelContributions).newPostal(cco, CommunicationChannelType.POSTAL_ADDRESS, country, state, addressLine1, addressLine2, addressLine3, postalCode, city);
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

    @Inject
    CountryRepository countryRepository;

    @Inject
    DemoCommunicationChannelOwner_newChannelContributions demoCommunicationChannelOwner_newChannelContributions;


    @javax.inject.Inject
    ClockService clockService;

}
