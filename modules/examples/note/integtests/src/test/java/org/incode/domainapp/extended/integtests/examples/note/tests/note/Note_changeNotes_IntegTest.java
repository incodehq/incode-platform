package org.incode.domainapp.extended.integtests.examples.note.tests.note;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.domainapp.extended.integtests.examples.note.NoteModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.dom.spiimpl.CalendarNameRepositoryForDemo;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.note.fixture.DemoModule_withNotes_tearDown;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.note.dom.impl.note.Note;

import static org.assertj.core.api.Assertions.assertThat;

public class Note_changeNotes_IntegTest extends NoteModuleIntegTestAbstract {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    DemoObjectMenu noteDemoObjectMenu;

    DemoObject notable;
    Note note;
    Note noteWithoutDate;
    Note noteWithoutText;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoModule_withNotes_tearDown(), null);

        notable = wrap(noteDemoObjectMenu).createDemoObject("Foo");
        calendarNameRepository.setCalendarNames(DemoObject.class, "BLUE", "GREEN", "RED");

        wrap(mixinAddNote(notable)).$$("note A", fakeData.jodaLocalDates().any(), "GREEN");
        wrap(mixinAddNote(notable)).$$("note B", null, null);
        wrap(mixinAddNote(notable)).$$(null, fakeData.jodaLocalDates().any(), "RED");

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


    public static class ActionImplementationIntegTest extends Note_changeNotes_IntegTest {

        @Test
        public void happy_case() throws Exception {

            // given
            final String notesBefore = wrap(note).getContent();

            final String newNotes = fakeData.lorem().paragraph();
            assertThat(newNotes).isNotEqualTo(notesBefore);

            // when
            wrap(mixinChangeNotes(note)).$$(newNotes);

            // then
            assertThat(wrap(note).getContent()).isEqualTo(newNotes);
        }
    }

    public static class DefaultIntegTest extends Note_changeNotes_IntegTest {

        @Test
        public void happy_case() throws Exception {

            // given
            final String notes = wrap(note).getContent();

            // when
            final String defaultNotes = mixinChangeNotes(note).default0$$();

            // then
            assertThat(defaultNotes).isEqualTo(notes);
        }
    }

    public static class ValidateIntegTest extends Note_changeNotes_IntegTest {

        @Test
        public void can_change_to_null_for_note_with_a_date() throws Exception {

            // given
            assertThat(wrap(note).getDate()).isNotNull();

            // when
            wrap(mixinChangeNotes(note)).$$(null);

            // then
            assertThat(wrap(note).getContent()).isNull();
        }

        @Test
        public void cannot_change_to_null_for_note_without_a_date() throws Exception {

            // given
            assertThat(wrap(noteWithoutDate).getDate()).isNull();

            // expecting
            expectedExceptions.expect(InvalidException.class);
            expectedExceptions.expectMessage("Must specify either note text or a date (or both)");

            // when
            wrap(mixinChangeNotes(noteWithoutDate)).$$(null);
        }
    }


}