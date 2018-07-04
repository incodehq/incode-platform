package org.incode.example.docfragment.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.docfragment.demo.shared.customer.fixture.DocFragCustomerData;
import org.incode.example.docfragment.demo.shared.invoicewithatpath.fixture.DocFragInvoiceData;

public class DocFragCustomer_and_DocFragInvoice_and_fragments_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocFragCustomerData.PersistScript());
        ec.executeChild(this, new DocFragInvoiceData.PersistScript());
        ec.executeChild(this, new DocFragmentData.PersistScript());

    }
}
