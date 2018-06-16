package org.incode.extended.integtests.examples.document.demo;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.extended.integtests.examples.document.demo.dom.demowithnotes.DemoObjectWithNotes;
import org.incode.extended.integtests.examples.document.demo.dom.demowithurl.DemoObjectWithUrl;
import org.incode.extended.integtests.examples.document.demo.dom.invoice.DemoInvoice;
import org.incode.extended.integtests.examples.document.demo.dom.order.DemoOrder;
import org.incode.extended.integtests.examples.document.demo.dom.order.DemoOrderLine;
import org.incode.extended.integtests.examples.document.demo.dom.other.OtherObject;

@XmlRootElement(name = "module")
public class DocumentModuleDemoDomSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                deleteFrom(DemoInvoice.class);
                deleteFrom(DemoObjectWithNotes.class);
                deleteFrom(DemoObjectWithUrl.class);
                deleteFrom(DemoOrderLine.class);
                deleteFrom(DemoOrder.class);
                deleteFrom(OtherObject.class);
            }
        };
    }
}
