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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteIntegTest extends NoteModuleIntegTest {

    Notable notable;
    Note note;
    Note noteWithoutDate;
    Note noteWithoutText;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        notable = wrap(noteDemoObjectMenu).create("Foo");
        calendarNameRepository.setCalendarNames(NoteDemoObject.class, "BLUE", "GREEN", "RED");

        wrap(noteContributionsOnNotable).addNote(notable, "note A", fakeData.jodaLocalDates().any(), "GREEN");
        wrap(noteContributionsOnNotable).addNote(notable, "note B", null, null);
        wrap(noteContributionsOnNotable).addNote(notable, null, fakeData.jodaLocalDates().any(), "RED");

        final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
        note = Iterables.find(noteList, x -> x.getNotes() != null && x.getDate() != null);
        noteWithoutDate = Iterables.find(noteList, x -> x.getDate() == null);
        noteWithoutText = Iterables.find(noteList, x -> x.getNotes() == null);
    }

    String anyOtherCalendarNameFor(final Notable notable, final String exclude) {
        for (String calendarName : calendarNameRepository.calendarNamesFor(notable)) {
            if(!calendarName.equals(exclude)) {
                return calendarName;
            }
        }
        throw new IllegalStateException("could not find any other calendar name");
    }

    public static class ChangeNotesIntegTest extends NoteIntegTest {

        public static class ActionImplementationIntegTest extends ChangeNotesIntegTest {

            @Test
            public void happy_case() throws Exception {

                // given
                final String notesBefore = wrap(note).getNotes();

                final String newNotes = fakeData.lorem().paragraph();
                assertThat(newNotes).isNotEqualTo(notesBefore);

                // when
                wrap(note).changeNotes(newNotes);

                // then
                assertThat(wrap(note).getNotes()).isEqualTo(newNotes);
            }
        }

        public static class DefaultIntegTest extends ChangeNotesIntegTest {

            @Test
            public void happy_case() throws Exception {

                // given
                final String notes = wrap(note).getNotes();

                // when
                final String defaultNotes = note.default0ChangeNotes();

                // then
                assertThat(defaultNotes).isEqualTo(notes);
            }
        }

        public static class ValidateIntegTest extends ChangeNotesIntegTest {

            @Test
            public void can_change_to_null_for_note_with_a_date() throws Exception {

                // given
                assertThat(wrap(note).getDate()).isNotNull();

                // when
                wrap(note).changeNotes(null);

                // then
                assertThat(wrap(note).getNotes()).isNull();
            }

            @Test
            public void cannot_change_to_null_for_note_without_a_date() throws Exception {

                // given
                assertThat(wrap(noteWithoutDate).getDate()).isNull();

                // expecting
                expectedException.expect(InvalidException.class);
                expectedException.expectMessage("Must specify either note text or a date (or both)");

                // when
                wrap(noteWithoutDate).changeNotes(null);
            }
        }

        public static class ChangeDateIntegTest extends NoteIntegTest {

            public static class ActionImplementationIntegTest extends ChangeDateIntegTest {

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

                    wrap(note).changeDate(newDate, newCalendarName);

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
                    wrap(note).changeDate(null, null);

                    // then
                    assertThat(wrap(note).getDate()).isNull();
                    assertThat(wrap(note).getCalendarName()).isNull();

                    // and (the notes still exist)
                    assertThat(noteContributionsOnNotable.notes(notable)).hasSize(3);

                    // however
                    assertThat(asList(noteRepository.findByNotableInDateRange(notable, date, date))).hasSize(0);
                    assertThat(asList(notableLinkRepository.findByNotableInDateRange(notable, date, date))).hasSize(0);

                }
            }

            public static class ChoicesIntegTest extends ChangeDateIntegTest {

                @Test
                public void lists_the_remaining_calendars_as_well_as_the_notes_current_one() throws Exception {

                    // given
                    final List<Note> notes = wrap(noteContributionsOnNotable).notes(notable);
                    final List<String> calendarNames = asList(Iterables.transform(notes, x -> x.getCalendarName()));
                    assertThat(calendarNames).containsAll(Arrays.asList("RED", "GREEN", null));

                    final String currentCalendarName = wrap(note).getCalendarName();

                    // when
                    final Collection<String> availableCalendarNames = note.choices1ChangeDate();

                    // then
                    assertThat(availableCalendarNames).hasSize(2);
                    assertThat(availableCalendarNames).containsAll(Arrays.asList("BLUE", currentCalendarName));
                }
            }

            public static class DefaultIntegTest extends ChangeDateIntegTest {

                @Test
                public void uses_current_values() throws Exception {

                    // when, then
                    assertThat(note.default0ChangeDate()).isEqualTo(note.getDate());

                    // when, then
                    assertThat(note.default1ChangeDate()).isEqualTo(note.getCalendarName());
                }
            }

            public static class ValidateIntegTest extends ChangeDateIntegTest {

                @Test
                public void cannot_set_date_to_null_when_has_no_text() throws Exception {

                    // expecting
                    expectedException.expect(InvalidException.class);
                    expectedException.expectMessage("Must specify either note text or a date/calendar (or both)");

                    // when
                    wrap(noteWithoutText).changeDate(null, null);
                }
            }
        }

        public static class RemoveIntegTest extends NoteIntegTest {

            public static class ActionImplementationIntegTest extends RemoveIntegTest {

                @Test
                public void happy_case() throws Exception {

                    // given
                    final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
                    assertThat(noteList).hasSize(3);

                    // when
                    wrap(note).remove(true);

                    // then
                    final List<Note> noteListAfter = wrap(noteContributionsOnNotable).notes(notable);
                    assertThat(noteListAfter).hasSize(2);

                }
            }

            public static class ValidateIntegTest extends RemoveIntegTest {

                @Test
                public void cannot_proceed_if_unchecked_are_you_sure() throws Exception {

                    // expecting
                    expectedException.expect(InvalidException.class);
                    expectedException.expectMessage("Check the 'are you sure' to continue");

                    // when
                    wrap(note).remove(false);
                }

                /**
                 * the areYouSure is a tristate boolean checkbox
                 */
                @Test
                public void cannot_proceed_if_ignore_are_you_sure() throws Exception {

                    // expecting
                    expectedException.expect(InvalidException.class);
                    expectedException.expectMessage("Check the 'are you sure' to continue");

                    // when
                    wrap(note).remove(null);
                }
            }

            public static class DomainEventIntegTest extends RemoveIntegTest {

                @DomainService(nature = NatureOfService.DOMAIN)
                public static class Subscriber extends AbstractSubscriber {

                    Note.RemoveEvent ev;

                    @Subscribe
                    public void on(Note.RemoveEvent ev) {
                        this.ev = ev;
                    }
                }

                @Inject
                Subscriber subscriber;

                @Test
                public void fires_event() throws Exception {

                    // when
                    wrap(note).remove(true);

                    // then
                    assertThat(subscriber.ev).isNotNull();
                    assertThat(subscriber.ev.getSource()).isSameAs(note);
                }

            }

        }

    }
}