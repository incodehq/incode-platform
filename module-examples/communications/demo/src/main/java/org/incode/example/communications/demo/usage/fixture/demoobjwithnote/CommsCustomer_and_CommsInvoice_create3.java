package org.incode.example.communications.demo.usage.fixture.demoobjwithnote;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import com.google.common.io.Resources;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Blob;

import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsCustomer;
import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsInvoice;
import org.incode.example.communications.demo.usage.contributions.DemoAppCommunicationChannelOwner_newChannelContributions;
import org.incode.example.communications.demo.usage.contributions.invoice.CommsInvoice_simulateRenderAsDoc;
import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsInvoiceRepository;
import org.incode.example.communications.demo.shared.demowithnotes.dom.CommsCustomerMenu;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelOwner;
import org.incode.example.communications.dom.impl.commchannel.CommunicationChannelType;
import org.incode.example.country.dom.impl.Country;
import org.incode.example.country.dom.impl.CountryRepository;
import org.incode.example.country.dom.impl.State;
import org.incode.example.country.fixture.CountriesRefData;
import org.incode.example.document.dom.impl.docs.Document;
import org.incode.example.document.dom.impl.docs.Document_attachSupportingPdf;

public class CommsCustomer_and_CommsInvoice_create3 extends FixtureScript {

    public static final String FRED_HAS_EMAIL_AND_PHONE = "Fred HasEmailAndPhone";
    public static final String MARY_HAS_PHONE_AND_POST = "Mary HasPhoneAndPost";
    public static final String JOE_HAS_EMAIL_AND_POST = "Joe HasPostAndEmail";


    @Override
    protected void execute(final ExecutionContext executionContext) {

        final Country gbrCountry = countryRepository.findCountry(CountriesRefData.GBR);

        final CommsCustomer custA = wrap(demoCustomerMenu).createDemoObjectWithNotes(FRED_HAS_EMAIL_AND_PHONE);
        addEmailAddress(custA, "fred@gmail.com");
        addEmailAddress(custA, "freddy@msn.com");
        addPhoneOrFaxNumber(custA, CommunicationChannelType.PHONE_NUMBER, "555 1234");
        addPhoneOrFaxNumber(custA, CommunicationChannelType.FAX_NUMBER, "555 4321");

        final CommsInvoice custA_1 = commsInvoiceRepository.create("1", custA);
        attachReceipt(custA_1, "Sample4.PDF");

        final CommsInvoice custA_2 = commsInvoiceRepository.create("2", custA);
        attachReceipt(custA_2, "Sample5.PDF");


        final CommsCustomer custB = wrap(demoCustomerMenu).createDemoObjectWithNotes(MARY_HAS_PHONE_AND_POST);
        addPhoneOrFaxNumber(custB, CommunicationChannelType.PHONE_NUMBER, "777 0987");
        addPhoneOrFaxNumber(custB, CommunicationChannelType.FAX_NUMBER, "777 7890");
        addPostalAddress(custB, gbrCountry, null, "45", "High Street", null, "OX1 4BJ", "Oxford");
        addPostalAddress(custB, gbrCountry, null, "23", "Railway Road", null, "WN7 4AA", "Leigh");

        final CommsInvoice custB_1 = commsInvoiceRepository.create("1", custB);
        attachReceipt(custB_1, "xlsdemo1.pdf");

        final CommsInvoice custB_2 = commsInvoiceRepository.create("2", custB);
        attachReceipt(custB_2, "xlsdemo2.pdf");

        final CommsCustomer custC = wrap(demoCustomerMenu).createDemoObjectWithNotes(JOE_HAS_EMAIL_AND_POST);
        addEmailAddress(custC, "joe@yahoo.com");
        addEmailAddress(custC, "joey@friends.com");
        addPostalAddress(custC, gbrCountry, null, "5", "Witney Gardens", null, "WA4 5HT", "Warrington");
        addPostalAddress(custC, gbrCountry, null, "3", "St. Nicholas Street Road", null, "YO11 2HF", "Scarborough");

        final CommsInvoice custC_1 = commsInvoiceRepository.create("1", custC);
        attachReceipt(custC_1, "pptdemo1.pdf");

        final CommsInvoice custC_2 = commsInvoiceRepository.create("2", custC);
        attachReceipt(custC_2, "pptdemo2.pdf");
    }

    private Document attachReceipt(final CommsInvoice invoice, final String resourceName) {
        final Blob blob = loadPdf(resourceName);
        try {
            return wrap(mixin(CommsInvoice_simulateRenderAsDoc.class, invoice)).$$(blob, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document attachPdf(final Document document, final String resourceName) {
        final Blob blob = loadPdf(resourceName);
        try {
            final Document_attachSupportingPdf attachPdf = mixin(Document_attachSupportingPdf.class, document);
            return wrap(attachPdf).exec(attachPdf.default0Exec(), blob, null, attachPdf.default3Exec());
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
                .getResource(CommsCustomer_and_CommsInvoice_create3.class, resourceName);
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


    @Inject
    CommsCustomerMenu demoCustomerMenu;

    @Inject
    CountryRepository countryRepository;

    @Inject
    CommsInvoiceRepository commsInvoiceRepository;

    @Inject
    DemoAppCommunicationChannelOwner_newChannelContributions demoAppCommunicationChannelOwner_newChannelContributions;

}
