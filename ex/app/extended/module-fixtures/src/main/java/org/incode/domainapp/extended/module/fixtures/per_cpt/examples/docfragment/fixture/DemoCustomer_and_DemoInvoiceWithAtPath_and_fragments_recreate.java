package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_recreate extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoModule_and_DocFragment_tearDown());
        ec.executeChild(this, new DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create());

    }
}
