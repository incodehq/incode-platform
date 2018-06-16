package org.incode.extended.integtests.examples.document.demo.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.extended.integtests.examples.document.demo.fixture.teardown.sub.DemoInvoice_tearDown;
import org.incode.extended.integtests.examples.document.demo.fixture.teardown.sub.DemoObjectWithNotes_tearDown;
import org.incode.extended.integtests.examples.document.demo.fixture.teardown.sub.DemoObjectWithUrl_tearDown;
import org.incode.extended.integtests.examples.document.demo.fixture.teardown.sub.DemoOrderAndOrderLine_tearDown;
import org.incode.extended.integtests.examples.document.demo.fixture.teardown.sub.OtherObject_tearDown;

public class DemoModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoObjectWithUrl_tearDown());

        executionContext.executeChild(this, new DemoObjectWithNotes_tearDown());
        executionContext.executeChild(this, new DemoOrderAndOrderLine_tearDown());
        executionContext.executeChild(this, new DemoInvoice_tearDown());

        executionContext.executeChild(this, new OtherObject_tearDown());
    }

}
