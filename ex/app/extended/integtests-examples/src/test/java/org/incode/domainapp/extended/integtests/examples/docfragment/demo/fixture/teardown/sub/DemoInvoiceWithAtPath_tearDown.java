package org.incode.domainapp.extended.integtests.examples.docfragment.demo.fixture.teardown.sub;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.integtests.examples.docfragment.demo.dom.invoicewithatpath.DemoInvoiceWithAtPath;

public class DemoInvoiceWithAtPath_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoInvoiceWithAtPath.class);
    }


}
