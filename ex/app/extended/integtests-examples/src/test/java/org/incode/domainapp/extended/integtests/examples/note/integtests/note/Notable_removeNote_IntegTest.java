package org.incode.domainapp.extended.integtests.examples.note.integtests.note;

import java.util.List;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.wrapper.DisabledException;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.dom.spiimpl.CalendarNameRepositoryForDemo;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.fixture.DemoModule_withNotes_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.note.dom.impl.notablelink.NotableLink;
import org.incode.example.note.dom.impl.notablelink.NotableLinkRepository;
import org.incode.example.note.dom.impl.note.Note;
import org.incode.example.note.dom.impl.note.NoteRepository;
import org.incode.example.note.dom.impl.note.T_removeNote;
import org.incode.domainapp.extended.integtests.examples.note.NoteModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Notable_removeNote_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    DemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteRepository noteRepository;

    @Inject
    NotableLinkRepository notableLinkRepository;


    DemoObject notable;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoModule_withNotes_tearDown(), null);

        notable = wrap(noteDemoObjectMenu).createDemoObject("Foo");
        calendarNameRepository.setCalendarNames(DemoObject.class, "BLUE", "GREEN", "RED");
    }

    public static class ActionImplementationIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void can_remove_note() throws Exception {

            // given
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> noteList = wrap(mixinNotes(notable)).$$();
            assertThat(noteList).hasSize(2);

            final List<Note> notes = noteRepository.findByNotable(notable);
            assertThat(notes).hasSize(2);

            final List<NotableLink> links = notableLinkRepository.findByNotable(notable);
            assertThat(links).hasSize(2);


            final Note someNote = fakeData.collections().anyOf(noteList.toArray(new Note[]{}));

            // when
            wrap(mixinRemoveNote(notable)).$$(someNote);

            // then
            final List<Note> noteListAfter = wrap(mixinNotes(notable)).$$();
            assertThat(noteListAfter).hasSize(1);
            assertThat(noteListAfter).doesNotContain(someNote);

            final List<Note> notesAfter = noteRepository.findByNotable(notable);
            assertThat(notesAfter).hasSize(1);

            final List<NotableLink> linksAfter = notableLinkRepository.findByNotable(notable);
            assertThat(linksAfter).hasSize(1);
        }

    }

    public static class DisableIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void disabled_if_none_exist() throws Exception {

            // given
            final List<Note> noteList = wrap(mixinNotes(notable)).$$();
            assertThat(noteList).isEmpty();

            // expecting
            expectedExceptions.expect(DisabledException.class);
            expectedExceptions.expectMessage("No content to remove");

            // when
            final Note note = null;
            wrap(mixinRemoveNote(notable)).$$(note);
        }

        @Test
        public void enabled_if_exist() throws Exception {

            // given
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "GREEN");
            final List<Note> noteList = wrap(mixinNotes(notable)).$$();
            assertThat(noteList).isNotEmpty();

            // expecting no errors

            // when
            final Note note = noteList.get(0);
            wrap(mixinRemoveNote(notable)).$$(note);
        }
    }

    public static class ChoicesIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void lists_notes_as_choices() throws Exception {

            // given
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> noteList = wrap(mixinNotes(notable)).$$();
            assertThat(noteList).hasSize(2);

            // when
            final List<Note> noteChoices = mixinRemoveNote(notable).choices0$$();

            // then
            assertThat(noteList).containsAll(noteChoices);
        }
    }

    public static class DefaultsIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void first_choice() throws Exception {

            // given
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> noteChoices = mixinRemoveNote(notable).choices0$$();

            // when
            final Note defaultChoice = mixinRemoveNote(notable).default0$$();

            // then
            assertThat(defaultChoice).isSameAs(noteChoices.get(0));
        }

    }

    public static class DomainEventTests extends Notable_removeNote_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class Subscriber extends AbstractSubscriber {

            T_removeNote.DomainEvent ev;

            @Subscribe
            public void on(T_removeNote.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        Subscriber subscriber;

        @Test
        public void fires_event() throws Exception {

            // given
            wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "GREEN");
            final List<Note> noteList = wrap(mixinNotes(notable)).$$();
            assertThat(noteList).isNotEmpty();

            // when
            final Note note = noteList.get(0);

            final T_removeNote mixinRemoveNote = mixinRemoveNote(notable);
            wrap(mixinRemoveNote).$$(note);

            // then
            assertThat(subscriber.ev).isNotNull();

            // no longer true, as per automatic dereferencing by wrapper factory in ISIS-1425
            // assertThat(subscriber.ev.getSource()).isSameAs(mixinRemoveNote);

            assertThat(subscriber.ev.getSource().getNotable()).isSameAs(notable);
            assertThat(subscriber.ev.getArguments().get(0)).isSameAs(note);
        }

    }

}