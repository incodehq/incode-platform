package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.fixture.order;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.dom.order.DemoOrder;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.dom.order.DemoOrderLine;

public class DemoOrderAndOrderLine_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final FixtureScript.ExecutionContext executionContext) {
        deleteFrom(DemoOrderLine.class);
        deleteFrom(DemoOrder.class);
    }

}
