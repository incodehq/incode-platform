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
import org.incode.module.note.dom.impl.note.Notable_addNote;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryForDemo;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Notable_addNote_IntegTest extends NoteModuleIntegTest {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;


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

    public static class ActionImplementationIntegTest extends Notable_addNote_IntegTest {

        @Test
        public void can_add_note_with_date_and_calendar_but_no_text() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();

            // when
            final LocalDate date = fakeData.jodaLocalDates().any();
            wrap(mixinAddNote(notable)).__(null, date, anyCalendarNameFor(notable));

            // then
            final List<Note> notes = wrap(mixinNotes(notable)).__();
            assertThat(notes).hasSize(1);
        }

        @Test
        public void can_add_to_two_different_calendars() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "BLUE");

            // when
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "GREEN");

            // then
            assertThat(wrap(mixinNotes(notable)).__()).hasSize(2);
        }

        @Test
        public void can_add_note_with_text_but_no_date_and_no_calendar() throws Exception {

            // given
            assertThat(mixinNotes(notable).__()).isEmpty();

            // when
            final String noteText = fakeData.lorem().paragraph();
            wrap(mixinAddNote(notable)).__(noteText, null, null);

            // then
            final List<Note> notes = wrap(mixinNotes(notable)).__();
            assertThat(notes).hasSize(1);

            assertThat(notes.get(0).getNotes()).isEqualTo(noteText);
        }

        @Test
        public void no_limit_to_adding_notes_not_associated_with_any_calendar() throws Exception {


            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "BLUE");
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "GREEN");

            // when
            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), null, null);
            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), null, null);
            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), null, null);

            // then
            final List<Note> notes = wrap(mixinNotes(notable)).__();
            assertThat(notes).hasSize(5);
        }

        @Test
        public void cannot_add_to_calendar_more_than_once() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "BLUE");

            // expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("This object already has a note on calendar 'BLUE'");

            // when
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "BLUE");
        }

    }

    public static class ChoicesIntegTest extends Notable_addNote_IntegTest {

        @Test
        public void filters_out_any_calendars_already_in_use() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "BLUE");
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "GREEN");

            // when
            final List<String> calendarNames = mixinAddNote(notable).choices2__();

            // then
            assertThat(calendarNames).hasSize(1);
            assertThat(calendarNames.get(0)).isEqualTo("RED");
        }

        @Test
        public void notes_without_a_calendar_are_effectively_ignored() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "BLUE");
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "GREEN");

            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), null, null);
            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), null, null);
            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), null, null);

            // when
            final List<String> calendarNames = mixinAddNote(notable).choices2__();

            // then
            assertThat(calendarNames).hasSize(1);
            assertThat(calendarNames.get(0)).isEqualTo("RED");

        }

    }

    public static class ValidateIntegTest extends Notable_addNote_IntegTest {

        @Test
        public void cannot_add_to_any_given_calendar_more_than_once() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "BLUE");
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "GREEN");

            // expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("already has a note on calendar 'GREEN'");

            // when
            wrap(mixinAddNote(notable)).__("", fakeData.jodaLocalDates().any(), "GREEN");
        }

        @Test
        public void cannot_add_note_with_a_date_but_no_calendar() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();

            // expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("Must also specify a calendar for the date");

            // when
            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), fakeData.jodaLocalDates().any(), null);
        }

        @Test
        public void cannot_add_note_with_a_calendar_but_no_date() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();

            // expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("Must also specify a date if calendar has been selected");

            // when
            wrap(mixinAddNote(notable)).__(fakeData.lorem().paragraph(), null, anyCalendarNameFor(notable));
        }

        @Test
        public void cannot_add_note_without_text_and_without_date() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();

            // expect
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("Must specify either note text or a date (or both)");

            // when
            wrap(mixinAddNote(notable)).__(null, null, anyCalendarNameFor(notable));
        }
    }

    public static class DomainEventIntegTest extends Notable_addNote_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class Subscriber extends AbstractSubscriber {

            Notable_addNote.Event ev;

            @Subscribe
            public void on(Notable_addNote.Event ev) {
                this.ev = ev;
            }
        }

        @Inject
        Subscriber subscriber;

        @Test
        public void fires_event() throws Exception {

            // given
            assertThat(wrap(mixinNotes(notable)).__()).isEmpty();

            // when
            final String text = fakeData.lorem().paragraph();
            final LocalDate date = fakeData.jodaLocalDates().any();
            final String calendarName = anyCalendarNameFor(notable);

            final Notable_addNote mixinAddNote = mixinAddNote(notable);
            wrap(mixinAddNote).__(text, date, calendarName);

            // then
            assertThat(subscriber.ev).isNotNull();
            assertThat(subscriber.ev.getSource()).isSameAs(mixinAddNote);
            assertThat(subscriber.ev.getSource().getNotable()).isSameAs(notable);
            assertThat(subscriber.ev.getArguments().get(0)).isEqualTo(text);
            assertThat(subscriber.ev.getArguments().get(1)).isEqualTo(date);
            assertThat(subscriber.ev.getArguments().get(2)).isEqualTo(calendarName);
        }
    }


}