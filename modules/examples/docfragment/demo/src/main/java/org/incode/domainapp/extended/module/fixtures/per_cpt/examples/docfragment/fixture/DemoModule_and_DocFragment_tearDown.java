package org.incode.example.alias.demo.examples.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoModule_and_DocFragment_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        //executionContext.executeChild(this, new DocFragmentModule_tearDown());
//        executionContext.executeChild(this, new DemoCustomer_tearDown());
//        executionContext.executeChild(this, new DemoInvoiceWithAtPath_tearDown());
    }

}
