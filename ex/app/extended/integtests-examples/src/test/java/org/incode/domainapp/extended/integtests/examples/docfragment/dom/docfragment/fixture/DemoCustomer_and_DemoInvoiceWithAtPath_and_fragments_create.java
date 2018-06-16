package org.incode.domainapp.extended.integtests.examples.docfragment.dom.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.integtests.examples.docfragment.demo.fixture.data.DemoCustomerData;
import org.incode.domainapp.extended.integtests.examples.docfragment.demo.fixture.data.DemoInvoiceWithAtPathData;
import org.incode.domainapp.extended.integtests.examples.docfragment.dom.docfragment.fixture.data.DocFragmentData;

public class DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoCustomerData.PersistScript());
        ec.executeChild(this, new DemoInvoiceWithAtPathData.PersistScript());
        ec.executeChild(this, new DocFragmentData.PersistScript());

    }
}
