package org.incode.module.note.integtests.note;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.NoteRepository;
import domainapp.modules.exampledom.module.note.dom.spiimpl.CalendarNameRepositoryForDemo;
import domainapp.modules.exampledom.module.note.dom.demo.NoteDemoObject;
import domainapp.modules.exampledom.module.note.dom.demo.NoteDemoObjectMenu;
import domainapp.modules.exampledom.module.note.fixture.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

public class Note_changeDate_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteRepository noteRepository;

    @Inject
    NotableLinkRepository notableLinkRepository;

    NoteDemoObject notable;
    Note note;
    Note noteWithoutDate;
    Note noteWithoutText;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        notable = wrap(noteDemoObjectMenu).create("Foo");
        calendarNameRepository.setCalendarNames(NoteDemoObject.class, "BLUE", "GREEN", "RED");

        final LocalDate someDate = fakeData.jodaLocalDates().any();
        final LocalDate someOtherDate = someDate.plusDays(7);
        wrap(mixinAddNote(notable)).$$("note A", someDate, "GREEN");
        wrap(mixinAddNote(notable)).$$("note B", null, null);
        wrap(mixinAddNote(notable)).$$(null, someOtherDate, "RED");

        final List<Note> noteList = wrap(mixinNotes(notable)).$$();
        note = Iterables.find(noteList, x -> x.getContent() != null && x.getDate() != null);
        noteWithoutDate = Iterables.find(noteList, x -> x.getDate() == null);
        noteWithoutText = Iterables.find(noteList, x -> x.getContent() == null);
    }

    String anyOtherCalendarNameFor(final Object notable, final String exclude) {
        for (String calendarName : calendarNameRepository.calendarNamesFor(notable)) {
            if(!calendarName.equals(exclude)) {
                return calendarName;
            }
        }
        throw new IllegalStateException("could not find any other calendar name");
    }

    public static class ActionImplementationIntegTest extends Note_changeDate_IntegTest {

        @Test
        public void change_date_when_already_has_one() throws Exception {

            // given
            final LocalDate date = wrap(note).getDate();
            final String calendarNameBefore = wrap(note).getCalendarName();

            final List<Note> noteList = asList(noteRepository.findByNotableInDateRange(notable, date, date));
            assertThat(noteList).hasSize(1);
            assertThat(noteList.get(0)).isSameAs(note);

            final List<NotableLink> linkList = asList(
                    notableLinkRepository.findByNotableInDateRange(notable, date, date));
            assertThat(linkList).hasSize(1);
            assertThat(linkList.get(0).getNote()).isSameAs(note);
            assertThat(linkList.get(0).getNotable()).isSameAs(notable);

            // when
            final LocalDate newDate = date.plusDays(fakeData.ints().between(10, 20));
            final String newCalendarName = anyOtherCalendarNameFor(notable, calendarNameBefore);

            wrap(mixinChangeDate(note)).$$(newDate, newCalendarName);

            // then
            assertThat(wrap(note).getDate()).isEqualTo(newDate);
            assertThat(wrap(note).getCalendarName()).isEqualTo(newCalendarName);

            // and
            assertThat(asList(noteRepository.findByNotableInDateRange(notable, date, date))).hasSize(0);

            // instead...
            final List<Note> noteListAfter = asList(
                    noteRepository.findByNotableInDateRange(notable, newDate, newDate));
            assertThat(noteListAfter).hasSize(1);
            assertThat(noteListAfter.get(0)).isSameAs(note);

            // and
            assertThat(asList(notableLinkRepository.findByNotableInDateRange(notable, date, date))).hasSize(0);

            // instead...
            final List<NotableLink> linkListAfter = asList(
                    notableLinkRepository.findByNotableInDateRange(notable, newDate, newDate));
            assertThat(linkListAfter).hasSize(1);
            assertThat(linkListAfter.get(0).getNote()).isSameAs(note);
            assertThat(linkListAfter.get(0).getNotable()).isSameAs(notable);
        }

        @Test
        public void set_date_to_null_when_has_text() throws Exception {

            // given
            final LocalDate date = wrap(note).getDate();

            final List<Note> noteList = asList(noteRepository.findByNotableInDateRange(notable, date, date));
            assertThat(noteList).hasSize(1);
            assertThat(noteList.get(0)).isSameAs(note);

            final List<NotableLink> linkList = asList(
                    notableLinkRepository.findByNotableInDateRange(notable, date, date));
            assertThat(linkList).hasSize(1);
            assertThat(linkList.get(0).getNote()).isSameAs(note);
            assertThat(linkList.get(0).getNotable()).isSameAs(notable);

            // when
            wrap(mixinChangeDate(note)).$$(null, null);

            // then
            assertThat(wrap(note).getDate()).isNull();
            assertThat(wrap(note).getCalendarName()).isNull();

            // and (the content still exist)
            assertThat(mixinNotes(notable).$$()).hasSize(3);

            // however
            assertThat(asList(noteRepository.findByNotableInDateRange(notable, date, date))).hasSize(0);
            assertThat(asList(notableLinkRepository.findByNotableInDateRange(notable, date, date))).hasSize(0);

        }
    }

    public static class ChoicesIntegTest extends Note_changeDate_IntegTest {

        @Test
        public void lists_the_remaining_calendars_as_well_as_the_notes_current_one() throws Exception {

            // given
            final List<Note> notes = wrap(mixinNotes(notable)).$$();
            final List<String> calendarNames = asList(Iterables.transform(notes, x -> x.getCalendarName()));
            assertThat(calendarNames).containsAll(Arrays.asList("RED", "GREEN", null));

            final String currentCalendarName = wrap(note).getCalendarName();

            // when
            final Collection<String> availableCalendarNames = mixinChangeDate(note).choices1$$();

            // then
            assertThat(availableCalendarNames).hasSize(2);
            assertThat(availableCalendarNames).containsAll(Arrays.asList("BLUE", currentCalendarName));
        }
    }

    public static class DefaultIntegTest extends Note_changeDate_IntegTest {

        @Test
        public void uses_current_values() throws Exception {

            // when, then
            assertThat(mixinChangeDate(note).default0$$()).isEqualTo(note.getDate());

            // when, then
            assertThat(mixinChangeDate(note).default1$$()).isEqualTo(note.getCalendarName());
        }
    }

    public static class ValidateIntegTest extends Note_changeDate_IntegTest {

        @Test
        public void cannot_set_date_to_null_when_has_no_text() throws Exception {

            // expecting
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("Must specify either note text or a date/calendar (or both)");

            // when
            wrap(mixinChangeDate(noteWithoutText)).$$(null, null);
        }
    }

}