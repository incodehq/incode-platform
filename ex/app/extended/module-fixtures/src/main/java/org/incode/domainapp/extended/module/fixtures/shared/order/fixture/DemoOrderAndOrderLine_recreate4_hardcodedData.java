package org.incode.domainapp.extended.module.fixtures.shared.order.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoOrderAndOrderLine_recreate4_hardcodedData extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new DemoOrderAndOrderLine_tearDown());
        executionContext.executeChild(this, new DemoOrderAndOrderLine_create4_hardcodedData());

    }


}
