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
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.NoteContributionsOnNotable;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteContributionsOnNotableTest extends NoteModuleIntegTest {

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

    public static class AddNoteTest extends NoteContributionsOnNotableTest {

        public static class ActionImplementationTest extends AddNoteTest {

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

        public static class ChoicesTests extends AddNoteTest {

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

        public static class ValidateTests extends AddNoteTest {

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

        public static class DomainEventTests extends AddNoteTest {

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
                wrap(noteContributionsOnNotable).addNote(notable, "demo", null, null);

                // then
                assertThat(subscriber.ev).isNotNull();
            }

        }
    }

}