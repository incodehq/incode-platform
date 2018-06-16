package org.incode.domainapp.extended.module.fixtures.shared.demowithnotes;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoInvoice;
import org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.dom.DemoObjectWithNotes;

@XmlRootElement(name = "module")
public class FixturesModuleSharedDemoWithNotesSubmodule extends ModuleAbstract {

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
