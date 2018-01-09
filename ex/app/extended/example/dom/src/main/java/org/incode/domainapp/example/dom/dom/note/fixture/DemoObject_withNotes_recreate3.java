package org.incode.domainapp.example.dom.dom.note.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoObject_withNotes_recreate3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new DemoModule_withNotes_tearDown());
        executionContext.executeChild(this, new DemoObject_withNotes_create3());

    }


}
