package org.incode.domainapp.extended.integtests.examples.note;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.fakedata.dom.FakeDataService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.FixturesModuleExamplesNoteIntegrationSubmodule;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.dom.demolink.NotableLinkForDemoObject_addNote;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.dom.demolink.NotableLinkForDemoObject_notes;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.dom.demolink.NotableLinkForDemoObject_removeNote;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.example.note.dom.impl.note.Note;
import org.incode.example.note.dom.impl.note.Note_changeDate;
import org.incode.example.note.dom.impl.note.Note_changeNotes;
import org.incode.example.note.dom.impl.note.Note_remove;
import org.incode.example.note.dom.impl.note.T_addNote;
import org.incode.example.note.dom.impl.note.T_notes;
import org.incode.example.note.dom.impl.note.T_removeNote;

public abstract class NoteModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    @XmlRootElement(name = "module")
    public static class MyModule extends ModuleAbstract {
        @Override
        public Set<Module> getDependencies() {
            return Sets.newHashSet(
                    new FixturesModuleExamplesNoteIntegrationSubmodule(),
                    new FakeDataModule()
            );
        }
    }

    public static ModuleAbstract module() {
        return new NoteModuleIntegTestAbstract.MyModule();
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
