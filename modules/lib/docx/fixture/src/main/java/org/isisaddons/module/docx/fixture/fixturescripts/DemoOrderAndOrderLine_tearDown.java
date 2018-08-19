package org.isisaddons.module.docx.fixture.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.docx.fixture.dom.demoorder.DemoOrder;
import org.isisaddons.module.docx.fixture.dom.demoorder.DemoOrderLine;

public class DemoOrderAndOrderLine_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final FixtureScript.ExecutionContext executionContext) {
        deleteFrom(DemoOrderLine.class);
        deleteFrom(DemoOrder.class);
    }

}
