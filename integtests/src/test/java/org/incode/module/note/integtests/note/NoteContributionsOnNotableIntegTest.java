/*
 *  Copyright 2014~2015 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.incode.module.note.integtests.note;

import java.util.List;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.NoteContributionsOnNotable;
import org.incode.module.note.dom.impl.note.NoteRepository;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryForDemo;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteContributionsOnNotableIntegTest extends NoteModuleIntegTest {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteRepository noteRepository;

    @Inject
    NotableLinkRepository notableLinkRepository;

    @Inject
    NoteContributionsOnNotable noteContributionsOnNotable;


    Notable notable;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        notable = wrap(noteDemoObjectMenu).create("Foo");
        calendarNameRepository.setCalendarNames(NoteDemoObject.class, "BLUE", "GREEN", "RED");
    }

    String anyCalendarNameFor(final Notable notable) {
        return fakeData.collections().anyOf(calendarNameRepository.calendarNamesFor(notable).toArray(new String[]{}));
    }

    public static class AddNoteIntegTest extends NoteContributionsOnNotableIntegTest {

        public static class ActionImplementationIntegTest extends AddNoteIntegTest {

            @Test
            public void can_add_note_with_date_and_calendar_but_no_text() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();

                // when
                final LocalDate date = fakeData.jodaLocalDates().any();
                wrap(noteContributionsOnNotable).addNote(notable, null, date, anyCalendarNameFor(notable));

                // then
                final List<Note> notes = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(notes).hasSize(1);
            }

            @Test
            public void can_add_to_two_different_calendars() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "BLUE");

                // when
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "GREEN");

                // then
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).hasSize(2);
            }

            @Test
            public void can_add_note_with_text_but_no_date_and_no_calendar() throws Exception {

                // given
                assertThat(noteContributionsOnNotable.notes(notable)).isEmpty();

                // when
                final String noteText = fakeData.lorem().paragraph();
                wrap(noteContributionsOnNotable).addNote(notable, noteText, null, null);

                // then
                final List<Note> notes = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(notes).hasSize(1);

                assertThat(notes.get(0).getNotes()).isEqualTo(noteText);
            }

            @Test
            public void no_limit_to_adding_notes_not_associated_with_any_calendar() throws Exception {


                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "BLUE");
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "GREEN");

                // when
                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), null, null);
                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), null, null);
                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), null, null);

                // then
                final List<Note> notes = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(notes).hasSize(5);
            }

            @Test
            public void cannot_add_to_calendar_more_than_once() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "BLUE");

                // expect
                expectedException.expect(InvalidException.class);
                expectedException.expectMessage("This object already has a note on calendar 'BLUE'");

                // when
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "BLUE");
            }

        }

        public static class ChoicesIntegTest extends AddNoteIntegTest {

            @Test
            public void filters_out_any_calendars_already_in_use() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "BLUE");
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "GREEN");

                // when
                final List<String> calendarNames = noteContributionsOnNotable.choices3AddNote(notable);

                // then
                assertThat(calendarNames).hasSize(1);
                assertThat(calendarNames.get(0)).isEqualTo("RED");
            }

            @Test
            public void notes_without_a_calendar_are_effectively_ignored() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "BLUE");
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "GREEN");

                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), null, null);
                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), null, null);
                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), null, null);

                // when
                final List<String> calendarNames = noteContributionsOnNotable.choices3AddNote(notable);

                // then
                assertThat(calendarNames).hasSize(1);
                assertThat(calendarNames.get(0)).isEqualTo("RED");

            }

        }

        public static class ValidateIntegTest extends AddNoteIntegTest {

            @Test
            public void cannot_add_to_any_given_calendar_more_than_once() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "BLUE");
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "GREEN");

                // expect
                expectedException.expect(InvalidException.class);
                expectedException.expectMessage("already has a note on calendar 'GREEN'");

                // when
                wrap(noteContributionsOnNotable).addNote(notable, "", fakeData.jodaLocalDates().any(), "GREEN");
            }

            @Test
            public void cannot_add_note_with_a_date_but_no_calendar() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();

                // expect
                expectedException.expect(InvalidException.class);
                expectedException.expectMessage("Must also specify a calendar for the date");

                // when
                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), fakeData.jodaLocalDates().any(), null);
            }

            @Test
            public void cannot_add_note_with_a_calendar_but_no_date() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();

                // expect
                expectedException.expect(InvalidException.class);
                expectedException.expectMessage("Must also specify a date if calendar has been selected");

                // when
                wrap(noteContributionsOnNotable).addNote(notable, fakeData.lorem().paragraph(), null, anyCalendarNameFor(notable));
            }

            @Test
            public void cannot_add_note_without_text_and_without_date() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();

                // expect
                expectedException.expect(InvalidException.class);
                expectedException.expectMessage("Must specify either note text or a date (or both)");

                // when
                wrap(noteContributionsOnNotable).addNote(notable, null, null, anyCalendarNameFor(notable));
            }
        }

        public static class DomainEventIntegTest extends AddNoteIntegTest {

            @DomainService(nature = NatureOfService.DOMAIN)
            public static class Subscriber extends AbstractSubscriber {

                NoteContributionsOnNotable.AddNoteEvent ev;

                @Subscribe
                public void on(NoteContributionsOnNotable.AddNoteEvent ev) {
                    this.ev = ev;
                }
            }

            @Inject
            Subscriber subscriber;

            @Test
            public void fires_event() throws Exception {

                // given
                assertThat(wrap(noteContributionsOnNotable).notes(notable)).isEmpty();

                // when
                final String text = fakeData.lorem().paragraph();
                final LocalDate date = fakeData.jodaLocalDates().any();
                final String calendarName = anyCalendarNameFor(notable);
                wrap(noteContributionsOnNotable).addNote(notable, text, date, calendarName);

                // then
                assertThat(subscriber.ev).isNotNull();
                assertThat(subscriber.ev.getSource()).isSameAs(noteContributionsOnNotable);
                assertThat(subscriber.ev.getArguments().get(0)).isSameAs(notable);
                assertThat(subscriber.ev.getArguments().get(1)).isEqualTo(text);
                assertThat(subscriber.ev.getArguments().get(2)).isEqualTo(date);
                assertThat(subscriber.ev.getArguments().get(3)).isEqualTo(calendarName);

            }

        }
    }

    public static class RemoveNoteIntegTest extends NoteContributionsOnNotableIntegTest {

        public static class ActionImplementationIntegTest extends RemoveNoteIntegTest {

            @Test
            public void can_remove_note() throws Exception {

                // given
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "GREEN");
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "BLUE");

                final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(noteList).hasSize(2);

                final List<Note> notes = noteRepository.findByNotable(notable);
                assertThat(notes).hasSize(2);

                final List<NotableLink> links = notableLinkRepository.findByNotable(notable);
                assertThat(links).hasSize(2);


                final Note someNote = fakeData.collections().anyOf(noteList.toArray(new Note[]{}));

                // when
                wrap(noteContributionsOnNotable).removeNote(notable, someNote);

                // then
                final List<Note> noteListAfter = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(noteListAfter).hasSize(1);
                assertThat(noteListAfter).doesNotContain(someNote);

                final List<Note> notesAfter = noteRepository.findByNotable(notable);
                assertThat(notesAfter).hasSize(1);

                final List<NotableLink> linksAfter = notableLinkRepository.findByNotable(notable);
                assertThat(linksAfter).hasSize(1);
            }

        }

        public static class DisableIntegTest extends RemoveNoteIntegTest {

            @Test
            public void disabled_if_none_exist() throws Exception {

                // given
                final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(noteList).isEmpty();

                // expecting
                expectedException.expect(DisabledException.class);
                expectedException.expectMessage("No notes to remove");

                // when
                final Note note = null;
                wrap(noteContributionsOnNotable).removeNote(notable, note);
            }

            @Test
            public void enabled_if_exist() throws Exception {

                // given
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "GREEN");
                final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(noteList).isNotEmpty();

                // expecting no errors

                // when
                final Note note = noteList.get(0);
                wrap(noteContributionsOnNotable).removeNote(notable, note);
            }
        }

        public static class ChoicesIntegTest extends RemoveNoteIntegTest {

            @Test
            public void lists_notes_as_choices() throws Exception {

                // given
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "GREEN");
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "BLUE");

                final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(noteList).hasSize(2);

                // when
                final List<Note> noteChoices = noteContributionsOnNotable.choices1RemoveNote(notable);

                // then
                assertThat(noteList).containsAll(noteChoices);
            }
        }

        public static class DefaultsIntegTest extends RemoveNoteIntegTest {

            @Test
            public void first_choice() throws Exception {

                // given
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "GREEN");
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "BLUE");

                final List<Note> noteChoices = noteContributionsOnNotable.choices1RemoveNote(notable);

                // when
                final Note defaultChoice = noteContributionsOnNotable.default1RemoveNote(notable);

                // then
                assertThat(defaultChoice).isSameAs(noteChoices.get(0));
            }

        }

        public static class DomainEventTests extends AddNoteIntegTest {

            @DomainService(nature = NatureOfService.DOMAIN)
            public static class Subscriber extends AbstractSubscriber {

                NoteContributionsOnNotable.RemoveNoteEvent ev;

                @Subscribe
                public void on(NoteContributionsOnNotable.RemoveNoteEvent ev) {
                    this.ev = ev;
                }
            }

            @Inject
            Subscriber subscriber;

            @Test
            public void fires_event() throws Exception {

                // given
                wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "GREEN");
                final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
                assertThat(noteList).isNotEmpty();

                // when
                final Note note = noteList.get(0);

                // when
                wrap(noteContributionsOnNotable).removeNote(notable, note);

                // then
                assertThat(subscriber.ev).isNotNull();
                assertThat(subscriber.ev.getSource()).isSameAs(noteContributionsOnNotable);
                assertThat(subscriber.ev.getArguments().get(0)).isSameAs(notable);
                assertThat(subscriber.ev.getArguments().get(1)).isSameAs(note);
            }

        }
    }

}