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

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.wrapper.DisabledException;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.notablelink.NotableLink;
import org.incode.module.note.dom.impl.notablelink.NotableLinkRepository;
import org.incode.module.note.dom.impl.note.Notable_removeNote;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.NoteRepository;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryForDemo;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Notable_removeNote_IntegTest extends NoteModuleIntegTest {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteRepository noteRepository;

    @Inject
    NotableLinkRepository notableLinkRepository;


    Notable notable;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        notable = wrap(noteDemoObjectMenu).create("Foo");
        calendarNameRepository.setCalendarNames(NoteDemoObject.class, "BLUE", "GREEN", "RED");
    }

    public static class ActionImplementationIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void can_remove_note() throws Exception {

            // given
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> noteList = wrap(mixinNotes(notable)).__();
            assertThat(noteList).hasSize(2);

            final List<Note> notes = noteRepository.findByNotable(notable);
            assertThat(notes).hasSize(2);

            final List<NotableLink> links = notableLinkRepository.findByNotable(notable);
            assertThat(links).hasSize(2);


            final Note someNote = fakeData.collections().anyOf(noteList.toArray(new Note[]{}));

            // when
            wrap(mixinRemoveNote(notable)).__(someNote);

            // then
            final List<Note> noteListAfter = wrap(mixinNotes(notable)).__();
            assertThat(noteListAfter).hasSize(1);
            assertThat(noteListAfter).doesNotContain(someNote);

            final List<Note> notesAfter = noteRepository.findByNotable(notable);
            assertThat(notesAfter).hasSize(1);

            final List<NotableLink> linksAfter = notableLinkRepository.findByNotable(notable);
            assertThat(linksAfter).hasSize(1);
        }

    }

    public static class DisableIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void disabled_if_none_exist() throws Exception {

            // given
            final List<Note> noteList = wrap(mixinNotes(notable)).__();
            assertThat(noteList).isEmpty();

            // expecting
            expectedException.expect(DisabledException.class);
            expectedException.expectMessage("No notes to remove");

            // when
            final Note note = null;
            wrap(mixinRemoveNote(notable)).__(note);
        }

        @Test
        public void enabled_if_exist() throws Exception {

            // given
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "GREEN");
            final List<Note> noteList = wrap(mixinNotes(notable)).__();
            assertThat(noteList).isNotEmpty();

            // expecting no errors

            // when
            final Note note = noteList.get(0);
            wrap(mixinRemoveNote(notable)).__(note);
        }
    }

    public static class ChoicesIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void lists_notes_as_choices() throws Exception {

            // given
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> noteList = wrap(mixinNotes(notable)).__();
            assertThat(noteList).hasSize(2);

            // when
            final List<Note> noteChoices = mixinRemoveNote(notable).choices0__();

            // then
            assertThat(noteList).containsAll(noteChoices);
        }
    }

    public static class DefaultsIntegTest extends Notable_removeNote_IntegTest {

        @Test
        public void first_choice() throws Exception {

            // given
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "GREEN");
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "BLUE");

            final List<Note> noteChoices = mixinRemoveNote(notable).choices0__();

            // when
            final Note defaultChoice = mixinRemoveNote(notable).default0__();

            // then
            assertThat(defaultChoice).isSameAs(noteChoices.get(0));
        }

    }

    public static class DomainEventTests extends Notable_removeNote_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class Subscriber extends AbstractSubscriber {

            Notable_removeNote.DomainEvent ev;

            @Subscribe
            public void on(Notable_removeNote.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        Subscriber subscriber;

        @Test
        public void fires_event() throws Exception {

            // given
            wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "GREEN");
            final List<Note> noteList = wrap(mixinNotes(notable)).__();
            assertThat(noteList).isNotEmpty();

            // when
            final Note note = noteList.get(0);

            final Notable_removeNote mixinRemoveNote = mixinRemoveNote(notable);
            wrap(mixinRemoveNote).__(note);

            // then
            assertThat(subscriber.ev).isNotNull();
            assertThat(subscriber.ev.getSource()).isSameAs(mixinRemoveNote);
            assertThat(subscriber.ev.getSource().getNotable()).isSameAs(notable);
            assertThat(subscriber.ev.getArguments().get(0)).isSameAs(note);
        }

    }

}