package org.incode.platform.dom.note.integtests;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.dom.note.dom.demolink.NotableLinkForDemoObject_addNote;
import org.incode.domainapp.example.dom.dom.note.dom.demolink.NotableLinkForDemoObject_notes;
import org.incode.domainapp.example.dom.dom.note.dom.demolink.NotableLinkForDemoObject_removeNote;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.Note_changeDate;
import org.incode.module.note.dom.impl.note.Note_changeNotes;
import org.incode.module.note.dom.impl.note.Note_remove;
import org.incode.module.note.dom.impl.note.T_addNote;
import org.incode.module.note.dom.impl.note.T_notes;
import org.incode.module.note.dom.impl.note.T_removeNote;
import org.incode.platform.dom.note.integtests.app.NoteModuleAppManifest;

public abstract class NoteModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                NoteModuleAppManifest.BUILDER
                        .withAdditionalModules(
                                ExampleDemoSubmodule.class,
                                NoteModuleIntegTestAbstract.class,
                                FakeDataModule.class
                        )
                        .build());
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
