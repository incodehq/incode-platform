package org.isisaddons.module.docx.fixture.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.docx.fixture.dom.demoorder.DocxDemoOrder;
import org.isisaddons.module.docx.fixture.dom.demoorder.DocxDemoOrderLine;

public class DocxDemoOrderAndOrderLine_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final FixtureScript.ExecutionContext executionContext) {
        deleteFrom(DocxDemoOrderLine.class);
        deleteFrom(DocxDemoOrder.class);
    }

}
