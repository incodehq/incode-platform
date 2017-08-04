package org.incode.domainapp.example.dom.demo.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoCustomerTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoInvoice2TearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoInvoiceTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAllTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAtPathTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithBlobTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithNotesTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithUrlTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoOrderAndOrderLineTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoToDoItem2TearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoToDoItemTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObjectTearDown;
import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.OtherObjectWithAtPathTearDown;

public class DemoModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DemoCustomerTearDown());
        executionContext.executeChild(this, new DemoInvoice2TearDown());
        executionContext.executeChild(this, new DemoInvoiceTearDown());
        executionContext.executeChild(this, new DemoObjectTearDown());
        executionContext.executeChild(this, new DemoObjectWithAllTearDown());
        executionContext.executeChild(this, new DemoObjectWithAtPathTearDown());
        executionContext.executeChild(this, new DemoObjectWithBlobTearDown());
        executionContext.executeChild(this, new DemoObjectWithNotesTearDown());
        executionContext.executeChild(this, new DemoObjectWithUrlTearDown());
        executionContext.executeChild(this, new DemoOrderAndOrderLineTearDown());
        executionContext.executeChild(this, new DemoToDoItem2TearDown());
        executionContext.executeChild(this, new DemoToDoItemTearDown());
        executionContext.executeChild(this, new OtherObjectTearDown());
        executionContext.executeChild(this, new OtherObjectWithAtPathTearDown());
    }

}
