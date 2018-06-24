package org.incode.example.note.integtests.tests.note;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.incode.example.note.integtests.NoteModuleIntegTestAbstract;
import org.incode.examples.note.demo.usage.dom.spiimpl.CalendarNameRepositoryForDemo;
import org.incode.examples.note.demo.usage.fixture.DemoModule_withNotes_tearDown;
import org.incode.examples.note.demo.shared.demo.dom.DemoObject;
import org.incode.examples.note.demo.shared.demo.dom.DemoObjectMenu;
import org.incode.example.note.dom.impl.notablelink.NotableLink;
import org.incode.example.note.dom.impl.notablelink.NotableLinkRepository;
import org.incode.example.note.dom.impl.note.Note;

import static org.assertj.core.api.Assertions.assertThat;

public class NotableLinkRepository_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    DemoObjectMenu noteDemoObjectMenu;

    @Inject
    NotableLinkRepository notableLinkRepository;

    DemoObject notable1;
    DemoObject notable2;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoModule_withNotes_tearDown(), null);

        notable1 = wrap(noteDemoObjectMenu).createDemoObject("Foo");
        notable2 = wrap(noteDemoObjectMenu).createDemoObject("Bar");

        calendarNameRepository.setCalendarNames(DemoObject.class, "BLUE", "GREEN", "RED");
    }

    public static class FindByNotableLinkIntegTest extends NotableLinkRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            wrap(mixinAddNote(notable1)).$$("note A", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable1)).$$("note B", fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> notes = wrap(mixinNotes(notable1)).$$();
            final Note note1 = Iterables.find(notes, input -> input.getContent().equals("note A"));
            final Note note2 = Iterables.find(notes, input -> input.getContent().equals("note B"));

            // when
            final NotableLink linkForNote1 = notableLinkRepository.findByNote(note1);

            // then
            assertThat(linkForNote1.getNote()).isEqualTo(note1);

            // when
            final NotableLink linkForNote2 = notableLinkRepository.findByNote(note2);

            // then
            assertThat(linkForNote2.getNote()).isEqualTo(note2);

        }
    }

    public static class FindByNotableIntegTest extends NotableLinkRepository_IntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            wrap(mixinAddNote(notable1)).$$("note A", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable1)).$$("note B", fakeData.jodaLocalDates().any(), "BLUE");

            wrap(mixinAddNote(notable2)).$$("note C", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable2)).$$("note D", fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> notable2Notes = wrap(mixinNotes(notable2)).$$();

            // when
            final List<NotableLink> links = notableLinkRepository.findByNotable(notable2);

            // then
            assertThat(links).hasSize(2);
            for (NotableLink link : links) {
                assertThat(link.getNotable()).isEqualTo(notable2);
                assertThat(notable2Notes).contains(link.getNote());
            }

        }
    }

    public static class FindByNotableAndCalendarNameIntegTest extends NotableLinkRepository_IntegTest {

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
            final NotableLink link1 = notableLinkRepository.findByNotableAndCalendarName(notable1, "GREEN");

            // then
            assertThat(link1).isNotNull();
            assertThat(link1.getNotable()).isEqualTo(notable1);
            assertThat(notable1Notes).contains(link1.getNote());

            // when
            final NotableLink link2 = notableLinkRepository.findByNotableAndCalendarName(notable2, "GREEN");

            // then
            assertThat(link2).isNotNull();
            assertThat(link2.getNotable()).isEqualTo(notable2);
            assertThat(notable2Notes).contains(link2.getNote());
        }
    }

    public static class FindByNotableInDateRangeIntegTest extends NotableLinkRepository_IntegTest {

        private LocalDate someDate;

        private List<Note> notable1Notes;

        @Before
        public void setUp() throws Exception {

            // given
            someDate = fakeData.jodaLocalDates().any();

            wrap(mixinAddNote(notable1)).$$("note A", someDate, "BLUE");
            wrap(mixinAddNote(notable1)).$$("note B", someDate.plusDays(2), "GREEN");
            wrap(mixinAddNote(notable1)).$$("note C", someDate.plusDays(-2), "RED");

            wrap(mixinAddNote(notable2)).$$("note D", someDate, "GREEN");

            notable1Notes = wrap(mixinNotes(notable1)).$$();
        }

        @Test
        public void single() throws Exception {

            // when
            final List<NotableLink> links = notableLinkRepository.findByNotableInDateRange(notable1, someDate, someDate);
            Note noteA = Iterables.find(notable1Notes, x -> x.getContent().equals("note A"));

            // then
            assertThat(links).hasSize(1);
            assertThat(links.get(0).getNote()).isSameAs(noteA);
            assertThat(links.get(0).getNotable()).isEqualTo(notable1);
        }

        @Test
        public void multiple() throws Exception {

            // when
            final List<NotableLink> links = notableLinkRepository.findByNotableInDateRange(notable1, someDate, someDate.plusDays(2));
            Note noteA = Iterables.find(notable1Notes, x -> x.getContent().equals("note A"));
            Note noteB = Iterables.find(notable1Notes, x -> x.getContent().equals("note B"));

            // then
            assertThat(links).hasSize(2);
            assertThat(links.get(0).getNote()).isSameAs(noteA);
            assertThat(links.get(0).getNotable()).isEqualTo(notable1);
            assertThat(links.get(1).getNote()).isSameAs(noteB);
            assertThat(links.get(1).getNotable()).isEqualTo(notable1);
        }

    }

}