package org.incode.example.alias.demo.examples.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.examples.commchannel.demo.shared.customer.fixture.DemoCustomerData;
import org.incode.examples.commchannel.demo.shared.invoicewithatpath.fixture.DemoInvoiceWithAtPathData;

public class DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoCustomerData.PersistScript());
        ec.executeChild(this, new DemoInvoiceWithAtPathData.PersistScript());
        ec.executeChild(this, new DocFragmentData.PersistScript());

    }
}
