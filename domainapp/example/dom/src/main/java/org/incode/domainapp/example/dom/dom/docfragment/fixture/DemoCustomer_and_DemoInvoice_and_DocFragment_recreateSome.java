package org.incode.domainapp.example.dom.dom.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.data.DemoCustomerData;
import org.incode.domainapp.example.dom.demo.fixture.data.DemoInvoiceData;
import org.incode.domainapp.example.dom.dom.docfragment.fixture.DemoModule_and_DocFragment_tearDown;
import org.incode.domainapp.example.dom.dom.docfragment.fixture.data.DocFragmentData;

public class DemoCustomer_and_DemoInvoice_and_DocFragment_recreateSome extends FixtureScript {

    public DemoCustomer_and_DemoInvoice_and_DocFragment_recreateSome() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        // execute
        ec.executeChild(this, new DemoModule_and_DocFragment_tearDown());
        ec.executeChild(this, new DemoCustomerData.PersistScript());
        ec.executeChild(this, new DemoInvoiceData.PersistScript());
        ec.executeChild(this, new DocFragmentData.PersistScript());

    }
}
