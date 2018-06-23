package org.incode.example.alias.demo.examples.note.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.example.alias.demo.examples.note.dom.demolink.NotableLinkForDemoObject;
import org.incode.example.alias.demo.shared.fixture.DemoObject_tearDown;
import org.incode.example.note.dom.impl.notablelink.NotableLink;
import org.incode.example.note.dom.impl.note.Note;

public class DemoModule_withNotes_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        deleteFrom(NotableLinkForDemoObject.class);
        deleteFrom(NotableLink.class);
        deleteFrom(Note.class);

        executionContext.executeChild(this, new DemoObject_tearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}