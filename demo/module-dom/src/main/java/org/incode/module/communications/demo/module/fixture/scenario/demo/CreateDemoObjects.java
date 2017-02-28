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
package org.incode.module.communications.demo.module.fixture.scenario.demo;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.module.communications.demo.module.dom.impl.demo.DemoCommunicationChannelOwner_newChannelContributions;
import org.incode.module.communications.demo.module.dom.impl.demo.DemoObject;
import org.incode.module.communications.demo.module.dom.impl.demo.DemoObjectMenu;
import org.incode.module.communications.demo.module.fixture.teardown.DemoModuleTearDown;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelType;
import org.incode.module.country.dom.impl.Country;
import org.incode.module.country.dom.impl.CountryRepository;
import org.incode.module.country.dom.impl.State;
import org.incode.module.country.fixture.CountriesRefData;

public class CreateDemoObjects extends DiscoverableFixtureScript {

    public CreateDemoObjects() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // teardown
        executionContext.executeChild(this, new DemoModuleTearDown());

        // prereqs
    	executionContext.executeChild(this, new CountriesRefData());


        final DemoObject foo = create("Foo", executionContext);

        wrapAddEmailAddress(foo, "foo@example.com");
        wrapAddPhoneOrFaxNumber(foo, CommunicationChannelType.PHONE_NUMBER, "555 1234");
        wrapAddPhoneOrFaxNumber(foo, CommunicationChannelType.FAX_NUMBER, "555 4321");

        final DemoObject bar = create("Bar", executionContext);
        wrapAddPhoneOrFaxNumber(bar, CommunicationChannelType.PHONE_NUMBER, "777 0987");

        final Country gbrCountry = countryRepository.findCountry(CountriesRefData.GBR);
        wrapAddPostalAddress(bar, gbrCountry, null, "45", "High Street", null, "OX1 4BJ", "Oxford");

        final DemoObject baz = create("Baz", executionContext);
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

    private DemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(demoObjectMenu).create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;

    @Inject
    CountryRepository countryRepository;

    @Inject
    DemoCommunicationChannelOwner_newChannelContributions demoCommunicationChannelOwner_newChannelContributions;


}
