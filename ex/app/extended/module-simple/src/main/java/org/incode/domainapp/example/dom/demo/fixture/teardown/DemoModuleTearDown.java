package org.incode.domainapp.example.dom.demo.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoCustomer_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoInvoice_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoInvoiceWithAtPath_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObject_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAll_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAtPath_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithBlob_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithNotes_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithUrl_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoOrderAndOrderLine_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoReminder_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoToDoItem_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObject_tearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObjectWithAtPath_tearDown;

public class DemoModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoCustomer_tearDown());
        executionContext.executeChild(this, new DemoInvoiceWithAtPath_tearDown());

        executionContext.executeChild(this, new DemoObject_tearDown());
        executionContext.executeChild(this, new DemoObjectWithAll_tearDown());
        executionContext.executeChild(this, new DemoObjectWithAtPath_tearDown());
        executionContext.executeChild(this, new DemoObjectWithBlob_tearDown());
        executionContext.executeChild(this, new DemoObjectWithUrl_tearDown());

        executionContext.executeChild(this, new DemoObjectWithNotes_tearDown());
        executionContext.executeChild(this, new DemoInvoice_tearDown());

        executionContext.executeChild(this, new DemoOrderAndOrderLine_tearDown());
        executionContext.executeChild(this, new DemoReminder_tearDown());
        executionContext.executeChild(this, new DemoToDoItem_tearDown());
        executionContext.executeChild(this, new OtherObject_tearDown());
        executionContext.executeChild(this, new OtherObjectWithAtPath_tearDown());
    }

}
