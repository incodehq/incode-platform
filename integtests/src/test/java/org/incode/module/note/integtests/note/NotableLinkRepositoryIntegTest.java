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

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

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

public class NotableLinkRepositoryIntegTest extends NoteModuleIntegTest {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Inject
    NotableLinkRepository notableLinkRepository;

    @Inject
    NoteContributionsOnNotable noteContributionsOnNotable;


    Notable notable1;
    Notable notable2;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        notable1 = wrap(noteDemoObjectMenu).create("Foo");
        notable2 = wrap(noteDemoObjectMenu).create("Bar");

        calendarNameRepository.setCalendarNames(NoteDemoObject.class, "BLUE", "GREEN", "RED");
    }

    public static class FindByNotableLinkIntegTest extends NotableLinkRepositoryIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            wrap(noteContributionsOnNotable).addNote(notable1, "note A", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(noteContributionsOnNotable).addNote(notable1, "note B", fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> notes = wrap(noteContributionsOnNotable).notes(notable1);
            final Note note1 = Iterables.find(notes, input -> input.getNotes().equals("note A"));
            final Note note2 = Iterables.find(notes, input -> input.getNotes().equals("note B"));

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

    public static class FindByNotableIntegTest extends NotableLinkRepositoryIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            wrap(noteContributionsOnNotable).addNote(notable1, "note A", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(noteContributionsOnNotable).addNote(notable1, "note B", fakeData.jodaLocalDates().any(), "BLUE");

            wrap(noteContributionsOnNotable).addNote(notable2, "note C", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(noteContributionsOnNotable).addNote(notable2, "note D", fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> notable2Notes = wrap(noteContributionsOnNotable).notes(notable2);

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

    public static class FindByNotableAndCalendarNameIntegTest extends NotableLinkRepositoryIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            wrap(noteContributionsOnNotable).addNote(notable1, "note A", fakeData.jodaLocalDates().any(), "BLUE");
            wrap(noteContributionsOnNotable).addNote(notable1, "note B", fakeData.jodaLocalDates().any(), "GREEN");
            wrap(noteContributionsOnNotable).addNote(notable1, "note C", fakeData.jodaLocalDates().any(), "RED");

            wrap(noteContributionsOnNotable).addNote(notable2, "note D", fakeData.jodaLocalDates().any(), "GREEN");

            final List<Note> notable1Notes = wrap(noteContributionsOnNotable).notes(notable1);
            final List<Note> notable2Notes = wrap(noteContributionsOnNotable).notes(notable2);

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

    public static class FindByNotableInDateRangeIntegTest extends NotableLinkRepositoryIntegTest {

        private LocalDate someDate;

        private List<Note> notable1Notes;
        private List<Note> notable2Notes;

        @Before
        public void setUp() throws Exception {

            // given
            someDate = fakeData.jodaLocalDates().any();

            wrap(noteContributionsOnNotable).addNote(notable1, "note A", someDate, "BLUE");
            wrap(noteContributionsOnNotable).addNote(notable1, "note B", someDate.plusDays(2), "GREEN");
            wrap(noteContributionsOnNotable).addNote(notable1, "note C", someDate.plusDays(-2), "RED");

            wrap(noteContributionsOnNotable).addNote(notable2, "note D", someDate, "GREEN");

            notable1Notes = wrap(noteContributionsOnNotable).notes(notable1);
            notable2Notes = wrap(noteContributionsOnNotable).notes(notable2);
        }

        @Test
        public void single() throws Exception {

            // when
            final List<NotableLink> links = notableLinkRepository.findByNotableInDateRange(notable1, someDate, someDate);
            Note noteA = Iterables.find(notable1Notes, x -> x.getNotes().equals("note A"));

            // then
            assertThat(links).hasSize(1);
            assertThat(links.get(0).getNote()).isSameAs(noteA);
            assertThat(links.get(0).getNotable()).isEqualTo(notable1);
        }

        @Test
        public void multiple() throws Exception {

            // when
            final List<NotableLink> links = notableLinkRepository.findByNotableInDateRange(notable1, someDate, someDate.plusDays(2));
            Note noteA = Iterables.find(notable1Notes, x -> x.getNotes().equals("note A"));
            Note noteB = Iterables.find(notable1Notes, x -> x.getNotes().equals("note B"));

            // then
            assertThat(links).hasSize(2);
            assertThat(links.get(0).getNote()).isSameAs(noteA);
            assertThat(links.get(0).getNotable()).isEqualTo(notable1);
            assertThat(links.get(1).getNote()).isSameAs(noteB);
            assertThat(links.get(1).getNotable()).isEqualTo(notable1);
        }

    }

}