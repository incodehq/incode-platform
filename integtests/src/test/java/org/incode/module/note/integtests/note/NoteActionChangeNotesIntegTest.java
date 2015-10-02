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

import com.google.common.collect.Iterables;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.NoteActionChangeNotes;
import org.incode.module.note.dom.impl.note.NoteContributionsOnNotable;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryForDemo;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteActionChangeNotesIntegTest extends NoteModuleIntegTest {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteContributionsOnNotable noteContributionsOnNotable;

    @Inject
    NoteActionChangeNotes noteActionChangeNotes;

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


    public static class ActionImplementationIntegTest extends NoteActionChangeNotesIntegTest {

        @Test
        public void happy_case() throws Exception {

            // given
            final String notesBefore = wrap(note).getNotes();

            final String newNotes = fakeData.lorem().paragraph();
            assertThat(newNotes).isNotEqualTo(notesBefore);

            // when
            wrap(noteActionChangeNotes).changeNotes(note, newNotes);

            // then
            assertThat(wrap(note).getNotes()).isEqualTo(newNotes);
        }
    }

    public static class DefaultIntegTest extends NoteActionChangeNotesIntegTest {

        @Test
        public void happy_case() throws Exception {

            // given
            final String notes = wrap(note).getNotes();

            // when
            final String defaultNotes = noteActionChangeNotes.default1ChangeNotes(note);

            // then
            assertThat(defaultNotes).isEqualTo(notes);
        }
    }

    public static class ValidateIntegTest extends NoteActionChangeNotesIntegTest {

        @Test
        public void can_change_to_null_for_note_with_a_date() throws Exception {

            // given
            assertThat(wrap(note).getDate()).isNotNull();

            // when
            wrap(noteActionChangeNotes).changeNotes(note, null);

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
            wrap(noteActionChangeNotes).changeNotes(noteWithoutDate, null);
        }
    }


}