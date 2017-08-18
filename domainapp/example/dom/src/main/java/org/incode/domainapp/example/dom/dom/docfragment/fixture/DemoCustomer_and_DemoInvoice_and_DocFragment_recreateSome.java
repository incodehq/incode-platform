package org.incode.domainapp.example.dom.dom.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoCustomer_and_DemoInvoice_and_DocFragment_recreateSome extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoModule_and_DocFragment_tearDown());
        ec.executeChild(this, new DemoCustomer_and_DemoInvoice_and_DocFragment_createSome());

    }
}
