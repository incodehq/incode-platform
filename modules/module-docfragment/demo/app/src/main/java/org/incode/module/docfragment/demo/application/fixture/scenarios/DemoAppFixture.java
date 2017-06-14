package org.incode.module.docfragment.demo.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.module.docfragment.demo.application.fixture.teardown.DemoAppTearDown;
import org.incode.module.docfragment.demo.module.fixture.customers.DemoCustomerData;
import org.incode.module.docfragment.demo.module.fixture.invoices.DemoInvoiceData;
import org.incode.module.docfragment.fixture.scenario.DocFragmentData;

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
