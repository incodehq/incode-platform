package org.incode.domainapp.example.dom.dom.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.data.DemoCustomerData;
import org.incode.domainapp.example.dom.demo.fixture.data.DemoInvoiceData;
import org.incode.domainapp.example.dom.dom.docfragment.fixture.data.DocFragmentData;

public class DemoAppFixture extends FixtureScript {

    public DemoAppFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        // execute
        ec.executeChild(this, new DemoAppTearDown());
        ec.executeChild(this, new DemoCustomerData.PersistScript());
        ec.executeChild(this, new DemoInvoiceData.PersistScript());
        ec.executeChild(this, new DocFragmentData.PersistScript());

    }
}
