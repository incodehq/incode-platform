package org.incode.domainapp.module.fixtures.per_cpt.examples.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.module.fixtures.shared.customer.fixture.DemoCustomer_tearDown;
import org.incode.domainapp.module.fixtures.shared.invoice.fixture.DemoInvoiceWithAtPath_tearDown;
import org.incode.domainapp.module.fixtures.per_cpt.examples.docfragment.fixture.sub.DocFragment_tearDown;

public class DemoModule_and_DocFragment_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DocFragment_tearDown());
        executionContext.executeChild(this, new DemoCustomer_tearDown());
        executionContext.executeChild(this, new DemoInvoiceWithAtPath_tearDown());
    }

}
