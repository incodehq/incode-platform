package org.incode.extended.integtests.examples.communications.demo.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.extended.integtests.examples.communications.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.extended.integtests.examples.communications.demo.dom.invoice.DemoInvoice;

public class DemoModuleTearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {

        deleteFrom(DemoInvoice.class);
        deleteFrom(DemoObjectWithNotes.class);

    }

}
