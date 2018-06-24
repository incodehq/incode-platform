package org.incode.example.note.integtests;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.examples.note.demo.usage.dom.demolink.NotableLinkForDemoObject_addNote;
import org.incode.examples.note.demo.usage.dom.demolink.NotableLinkForDemoObject_notes;
import org.incode.examples.note.demo.usage.dom.demolink.NotableLinkForDemoObject_removeNote;
import org.incode.examples.note.demo.shared.demo.dom.DemoObject;
import org.incode.example.note.dom.impl.note.Note;
import org.incode.example.note.dom.impl.note.Note_changeDate;
import org.incode.example.note.dom.impl.note.Note_changeNotes;
import org.incode.example.note.dom.impl.note.Note_remove;
import org.incode.example.note.dom.impl.note.T_addNote;
import org.incode.example.note.dom.impl.note.T_notes;
import org.incode.example.note.dom.impl.note.T_removeNote;

public abstract class NoteModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public static ModuleAbstract module() {
        return new NoteModuleIntegTestModule();
    }

    protected NoteModuleIntegTestAbstract() {
        super(module());
    }


    @Inject
    protected FakeDataService fakeData;

    protected T_addNote mixinAddNote(final DemoObject notable) {
        return mixin(NotableLinkForDemoObject_addNote.class, notable);
    }
    protected T_removeNote mixinRemoveNote(final DemoObject notable) {
        return mixin(NotableLinkForDemoObject_removeNote.class, notable);
    }

    protected T_notes mixinNotes(final DemoObject notable) {
        return mixin(NotableLinkForDemoObject_notes.class, notable);
    }

    protected Note_changeDate mixinChangeDate(final Note note) {
        return mixin(Note_changeDate.class, note);
    }
    protected Note_changeNotes mixinChangeNotes(final Note note) {
        return mixin(Note_changeNotes.class, note);
    }
    protected Note_remove mixinRemove(final Note note) {
        return mixin(Note_remove.class, note);
    }

    protected static <T> List<T> asList(final Iterable<T> iterable) {
        return Lists.newArrayList(iterable);
    }

}
