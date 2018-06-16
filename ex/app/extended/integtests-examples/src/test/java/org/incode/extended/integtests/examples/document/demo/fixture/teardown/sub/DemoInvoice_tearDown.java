package org.incode.extended.integtests.examples.document.demo.fixture.teardown.sub;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.extended.integtests.examples.document.demo.dom.invoice.DemoInvoice;

public class DemoInvoice_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoInvoice.class);
    }

}
