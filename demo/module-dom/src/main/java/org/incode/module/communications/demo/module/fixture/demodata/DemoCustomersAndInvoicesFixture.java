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
package org.incode.module.communications.demo.module.fixture.demodata;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import com.google.common.io.Resources;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.module.communications.demo.module.dom.impl.DemoAppCommunicationChannelOwner_newChannelContributions;
import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomer;
import org.incode.module.communications.demo.module.dom.impl.customers.DemoCustomerMenu;
import org.incode.module.communications.demo.module.dom.impl.invoices.DemoInvoice;
import org.incode.module.communications.demo.module.dom.impl.invoices.DemoInvoiceRepository;
import org.incode.module.communications.demo.module.dom.impl.invoices.DemoInvoice_attachReceipt;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelOwner;
import org.incode.module.communications.dom.impl.commchannel.CommunicationChannelType;
import org.incode.module.country.dom.impl.Country;
import org.incode.module.country.dom.impl.CountryRepository;
import org.incode.module.country.dom.impl.State;
import org.incode.module.country.fixture.CountriesRefData;

public class DemoCustomersAndInvoicesFixture extends FixtureScript {


    @Override
    protected void execute(final ExecutionContext executionContext) {

        final Country gbrCountry = countryRepository.findCountry(CountriesRefData.GBR);


        final DemoCustomer custA = newCustomer(executionContext);

        addEmailAddress(custA, "foo@example.com");
        addPhoneOrFaxNumber(custA, CommunicationChannelType.PHONE_NUMBER, "555 1234");
        addPhoneOrFaxNumber(custA, CommunicationChannelType.FAX_NUMBER, "555 4321");

        final DemoInvoice custA_1 = demoInvoiceRepository.create("1", custA);
        final DemoInvoice custA_2 = demoInvoiceRepository.create("2", custA);

        attachReceipt(custA_1, "xlsdemo1.PDF");
        attachReceipt(custA_2, "Sample4.PDF");



        final DemoCustomer custB = newCustomer(executionContext);
        addPhoneOrFaxNumber(custB, CommunicationChannelType.PHONE_NUMBER, "777 0987");
        addPostalAddress(custB, gbrCountry, null, "45", "High Street", null, "OX1 4BJ", "Oxford");

        final DemoInvoice custB_1 = demoInvoiceRepository.create("1", custB);
        final DemoInvoice custB_2 = demoInvoiceRepository.create("2", custB);

        attachReceipt(custB_1, "xlsdemo2.PDF");
        attachReceipt(custB_2, "Sample5.PDF");



        final DemoCustomer custC = newCustomer(executionContext);

        final DemoInvoice custC_1 = demoInvoiceRepository.create("1", custC);
        final DemoInvoice custC_2 = demoInvoiceRepository.create("2", custC);

    }

    private void attachReceipt(final DemoInvoice invoice, final String resourceName) {

        final Blob blob = loadPdf(resourceName);
        try {
            wrap(mixin(DemoInvoice_attachReceipt.class, invoice)).$$(blob, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Blob loadPdf(final String resourceName) {
        final byte[] bytes = loadResourceBytes(resourceName);
        return new Blob(resourceName, "application/pdf", bytes);
    }

    private static byte[] loadResourceBytes(final String resourceName) {
        final URL templateUrl = Resources
                .getResource(DemoCustomersAndInvoicesFixture.class, resourceName);
        try {
            return Resources.toByteArray(templateUrl);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unable to read resource URL '%s'", templateUrl));
        }
    }

    void addEmailAddress(final CommunicationChannelOwner cco, final String address) {
        wrap(demoAppCommunicationChannelOwner_newChannelContributions).newEmail(cco, CommunicationChannelType.EMAIL_ADDRESS, address);
    }

    void addPhoneOrFaxNumber(
            final CommunicationChannelOwner cco,
            final CommunicationChannelType type,
            final String number) {
        wrap(demoAppCommunicationChannelOwner_newChannelContributions).newPhoneOrFax(cco, type, number);
    }

    void addPostalAddress(
            final CommunicationChannelOwner cco,
            final Country country,
            final State state,
            final String addressLine1,
            final String addressLine2,
            final String addressLine3,
            final String postalCode,
            final String city) {
        wrap(demoAppCommunicationChannelOwner_newChannelContributions).newPostal(cco, CommunicationChannelType.POSTAL_ADDRESS, country, state, addressLine1, addressLine2, addressLine3, postalCode, city);
    }


    // //////////////////////////////////////

    private DemoCustomer newCustomer(
            final ExecutionContext executionContext) {

        final String name = fakeDataService.name().firstName();

        return executionContext.addResult(this, wrap(demoCustomerMenu).create(name));
    }

    // //////////////////////////////////////

    @Inject
    DemoCustomerMenu demoCustomerMenu;

    @Inject
    CountryRepository countryRepository;

    @javax.inject.Inject
    DemoInvoiceRepository demoInvoiceRepository;

    @javax.inject.Inject
    FakeDataService fakeDataService;

    @Inject
    DemoAppCommunicationChannelOwner_newChannelContributions demoAppCommunicationChannelOwner_newChannelContributions;


}
