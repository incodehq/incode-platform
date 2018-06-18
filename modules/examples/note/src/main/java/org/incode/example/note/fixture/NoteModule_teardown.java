package org.incode.example.note.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.note.dom.impl.notablelink.NotableLink;
import org.incode.example.note.dom.impl.note.Note;

public class NoteModule_teardown extends TeardownFixtureAbstract2 {
    @Override protected void execute(final ExecutionContext executionContext) {
        deleteFrom(NotableLink.class);
        deleteFrom(Note.class);
    }
}
