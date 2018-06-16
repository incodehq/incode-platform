package org.incode.extended.integtests.examples.note.integtests.note;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.dom.spiimpl.CalendarNameRepositoryForDemo;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.fixture.DemoModule_withNotes_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.note.dom.impl.note.Note;
import org.incode.example.note.dom.impl.note.NoteRepository;
import org.incode.extended.integtests.examples.note.NoteModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteRepository_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    DemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteRepository noteRepository;

    DemoObject notable1;
    DemoObject notable2;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoModule_withNotes_tearDown(), null);

        notable1 = wrap(noteDemoObjectMenu).createDemoObject("Foo");
        notable2 = wrap(noteDemoObjectMenu).createDemoObject("Bar");

        calendarNameRepository.setCalendarNames(DemoObject.class, "BLUE", "GREEN", "RED");
    }

    public static class FindByNotableIntegTest extends NoteRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            wrap(mixinAddNote(notable1)).$$("note A", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable1)).$$("note B", fakeData.jodaLocalDates().any(), "BLUE");

            wrap(mixinAddNote(notable2)).$$("note C", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable2)).$$("note D", fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> notable2Notes = wrap(mixinNotes(notable2)).$$();

            // when
            final List<Note> noteList = noteRepository.findByNotable(notable2);

            // then
            assertThat(noteList).hasSize(2);
            for (Note note : noteList) {
                assertThat(note.getNotable()).isEqualTo(notable2);
                assertThat(notable2Notes).contains(note);
            }
        }

    }

    public static class FindByNotableAndCalendarNameIntegTest extends NoteRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            wrap(mixinAddNote(notable1)).$$("note A", fakeData.jodaLocalDates().any(), "BLUE");
            wrap(mixinAddNote(notable1)).$$("note B", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable1)).$$("note C", fakeData.jodaLocalDates().any(), "RED");

            wrap(mixinAddNote(notable2)).$$("note D", fakeData.jodaLocalDates().any(), "GREEN");

            final List<Note> notable1Notes = wrap(mixinNotes(notable1)).$$();
            final List<Note> notable2Notes = wrap(mixinNotes(notable2)).$$();

            // when
            final Note note1 = noteRepository.findByNotableAndCalendarName(notable1, "GREEN");

            // then
            assertThat(note1).isNotNull();
            assertThat(note1.getNotable()).isEqualTo(notable1);
            assertThat(notable1Notes).contains(note1);

            // when
            final Note note2 = noteRepository.findByNotableAndCalendarName(notable2, "GREEN");

            // then
            assertThat(note2).isNotNull();
            assertThat(note2.getNotable()).isEqualTo(notable2);
            assertThat(notable2Notes).contains(note2);
        }
    }

    public static class FindByNotableInDateRangeIntegTest extends NoteRepository_IntegTest {

        private LocalDate someDate;

        private List<Note> notable1Notes;
        private List<Note> notable2Notes;

        @Before
        public void setUp() throws Exception {

            // given
            someDate = fakeData.jodaLocalDates().any();

            wrap(mixinAddNote(notable1)).$$("note A", someDate, "BLUE");
            wrap(mixinAddNote(notable1)).$$("note B", someDate.plusDays(2), "GREEN");
            wrap(mixinAddNote(notable1)).$$("note C", someDate.plusDays(-2), "RED");

            wrap(mixinAddNote(notable2)).$$("note D", someDate, "GREEN");

            notable1Notes = wrap(mixinNotes(notable1)).$$();
            notable2Notes = wrap(mixinNotes(notable2)).$$();
        }

        @Test
        public void single() throws Exception {

            // when
            final List<Note> notes = asList(noteRepository.findByNotableInDateRange(notable1, someDate, someDate));
            final Note noteA = Iterables.find(notable1Notes, x -> x.getContent().equals("note A"));

            // then
            assertThat(notes).hasSize(1);
            assertThat(notes.get(0)).isSameAs(noteA);
            assertThat(notes.get(0).getNotable()).isEqualTo(notable1);
        }

        @Test
        public void multiple() throws Exception {

            // when
            final List<Note> notes = asList(noteRepository.findByNotableInDateRange(notable1, someDate, someDate.plusDays(2)));
            Note noteA = Iterables.find(notable1Notes, x -> x.getContent().equals("note A"));
            Note noteB = Iterables.find(notable1Notes, x -> x.getContent().equals("note B"));

            // then
            assertThat(notes).hasSize(2);
            assertThat(notes.get(0)).isSameAs(noteA);
            assertThat(notes.get(0).getNotable()).isEqualTo(notable1);
            assertThat(notes.get(1)).isSameAs(noteB);
            assertThat(notes.get(1).getNotable()).isEqualTo(notable1);
        }
    }

    public static class FindInDateRangeIntegTest extends NoteRepository_IntegTest {

        private LocalDate someDate;

        private List<Note> notable1Notes;
        private List<Note> notable2Notes;

        @Before
        public void setUp() throws Exception {

            // given
            someDate = fakeData.jodaLocalDates().any();

            wrap(mixinAddNote(notable1)).$$("note A", someDate, "BLUE");
            wrap(mixinAddNote(notable1)).$$("note B", someDate.plusDays(2), "GREEN");
            wrap(mixinAddNote(notable1)).$$("note C", someDate.plusDays(-2), "RED");

            wrap(mixinAddNote(notable2)).$$("note D", someDate, "GREEN");

            notable1Notes = wrap(mixinNotes(notable1)).$$();
            notable2Notes = wrap(mixinNotes(notable2)).$$();
        }

        @Test
        public void single() throws Exception {

            // when
            final List<Note> notes = asList(noteRepository.findInDateRange(someDate.plusDays(1), someDate.plusDays(2)));
            Note noteB = Iterables.find(notable1Notes, x -> x.getContent().equals("note B"));

            // then
            assertThat(notes).hasSize(1);
            assertThat(notes.get(0)).isSameAs(noteB);
            assertThat(notes.get(0).getNotable()).isEqualTo(notable1);
        }

        @Test
        public void multiple() throws Exception {

            // when
            final List<Note> notes = asList(noteRepository.findInDateRange(someDate.plusDays(-1), someDate.plusDays(+1)));
            Note noteA = Iterables.find(notable1Notes, x -> x.getContent().equals("note A"));
            Note noteD = Iterables.find(notable2Notes, x -> x.getContent().equals("note D"));

            // then
            assertThat(notes).hasSize(2);
            assertThat(notes.get(0)).isSameAs(noteA);
            assertThat(notes.get(0).getNotable()).isEqualTo(notable1);
            assertThat(notes.get(1)).isSameAs(noteD);
            assertThat(notes.get(1).getNotable()).isEqualTo(notable2);
        }

    }

}