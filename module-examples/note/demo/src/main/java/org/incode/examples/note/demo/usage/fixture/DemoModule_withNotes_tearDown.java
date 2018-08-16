package org.incode.examples.note.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.examples.note.demo.usage.dom.demolink.NotableLinkForNotableObject;
import org.incode.examples.note.demo.shared.demo.fixture.NotableObject_tearDown;
import org.incode.example.note.dom.impl.notablelink.NotableLink;
import org.incode.example.note.dom.impl.note.Note;

public class DemoModule_withNotes_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        deleteFrom(NotableLinkForNotableObject.class);
        deleteFrom(NotableLink.class);
        deleteFrom(Note.class);

        executionContext.executeChild(this, new NotableObject_tearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
