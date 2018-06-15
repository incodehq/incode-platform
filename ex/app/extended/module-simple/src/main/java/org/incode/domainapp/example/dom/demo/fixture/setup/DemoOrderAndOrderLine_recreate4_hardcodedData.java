package org.incode.domainapp.example.dom.demo.fixture.setup;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoOrderAndOrderLine_tearDown;

public class DemoOrderAndOrderLine_recreate4_hardcodedData extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new DemoOrderAndOrderLine_tearDown());
        executionContext.executeChild(this, new DemoOrderAndOrderLine_create4_hardcodedData());

    }


}
