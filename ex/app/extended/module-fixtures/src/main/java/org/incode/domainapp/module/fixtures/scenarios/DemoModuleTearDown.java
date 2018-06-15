package org.incode.domainapp.module.fixtures.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.module.fixtures.shared.customer.fixture.DemoCustomer_tearDown;
import org.incode.domainapp.module.fixtures.shared.invoice.fixture.DemoInvoice_tearDown;
import org.incode.domainapp.module.fixtures.shared.invoice.fixture.DemoInvoiceWithAtPath_tearDown;
import org.incode.domainapp.module.fixtures.shared.demo.fixture.DemoObject_tearDown;
import org.incode.domainapp.module.fixtures.shared.demowithall.fixture.DemoObjectWithAll_tearDown;
import org.incode.domainapp.module.fixtures.shared.demowithatpath.fixture.DemoObjectWithAtPath_tearDown;
import org.incode.domainapp.module.fixtures.shared.demowithblob.fixture.DemoObjectWithBlob_tearDown;
import org.incode.domainapp.module.fixtures.shared.demowithnotes.fixture.DemoObjectWithNotes_tearDown;
import org.incode.domainapp.module.fixtures.shared.demowithurl.fixture.DemoObjectWithUrl_tearDown;
import org.incode.domainapp.module.fixtures.shared.order.fixture.DemoOrderAndOrderLine_tearDown;
import org.incode.domainapp.module.fixtures.shared.reminder.fixture.DemoReminder_tearDown;
import org.incode.domainapp.module.fixtures.shared.todo.fixture.DemoToDoItem_tearDown;
import org.incode.domainapp.module.fixtures.shared.other.fixture.OtherObject_tearDown;
import org.incode.domainapp.module.fixtures.shared.otherwithatpath.fixture.OtherObjectWithAtPath_tearDown;

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
