package org.incode.example.communications.demo.shared.demowithnotes;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.communications.demo.shared.demowithnotes.dom.DemoObjectWithNotes;
import org.incode.example.communications.demo.shared.demowithnotes.dom.DemoInvoice;

@XmlRootElement(name = "module")
public class CommunicationsDemoSharedDemoWithNotesSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                deleteFrom(DemoInvoice.class);
                deleteFrom(DemoObjectWithNotes.class);
            }
        };
    }
}
