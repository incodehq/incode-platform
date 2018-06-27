package org.incode.example.document.demo.shared.demowithnotes;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.document.demo.shared.demowithnotes.dom.DocInvoice;
import org.incode.example.document.demo.shared.demowithnotes.dom.DocDemoObjectWithNotes;

@XmlRootElement(name = "module")
public class DocumentDemoSharedDemoWithNotesSubmodule extends ModuleAbstract {

    @Override public FixtureScript getTeardownFixture() {
        return new TeardownFixtureAbstract2() {
            @Override
            protected void execute(final ExecutionContext executionContext) {
                deleteFrom(DocInvoice.class);
                deleteFrom(DocDemoObjectWithNotes.class);
            }
        };
    }
}
