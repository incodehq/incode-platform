package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.customer.fixture.DemoCustomerData;
import org.incode.domainapp.extended.module.fixtures.shared.invoicewithatpath.fixture.DemoInvoiceWithAtPathData;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment.fixture.data.DocFragmentData;

public class DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoCustomerData.PersistScript());
        ec.executeChild(this, new DemoInvoiceWithAtPathData.PersistScript());
        ec.executeChild(this, new DocFragmentData.PersistScript());

    }
}
