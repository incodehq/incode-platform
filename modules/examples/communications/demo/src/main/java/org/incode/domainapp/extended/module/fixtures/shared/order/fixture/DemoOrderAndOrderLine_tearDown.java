package org.incode.examples.commchannel.demo.shared.order.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.commchannel.demo.shared.order.dom.DemoOrder;
import org.incode.examples.commchannel.demo.shared.order.dom.DemoOrderLine;

public class DemoOrderAndOrderLine_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final FixtureScript.ExecutionContext executionContext) {
        deleteFrom(DemoOrderLine.class);
        deleteFrom(DemoOrder.class);
    }

}
