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
import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.wrapper.InvalidException;

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.NoteActionRemove;
import org.incode.module.note.dom.impl.note.NoteContributionsOnNotable;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryForDemo;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteActionRemoveIntegTest extends NoteModuleIntegTest {

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteContributionsOnNotable noteContributionsOnNotable;

    @Inject
    NoteActionRemove noteActionRemove;

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


    public static class ActionImplementationIntegTest extends NoteActionRemoveIntegTest {

        @Test
        public void happy_case() throws Exception {

            // given
            final List<Note> noteList = wrap(noteContributionsOnNotable).notes(notable);
            assertThat(noteList).hasSize(3);

            // when
            wrap(noteActionRemove).remove(note, true);

            // then
            final List<Note> noteListAfter = wrap(noteContributionsOnNotable).notes(notable);
            assertThat(noteListAfter).hasSize(2);

        }
    }

    public static class ValidateIntegTest extends NoteActionRemoveIntegTest {

        @Test
        public void cannot_proceed_if_unchecked_are_you_sure() throws Exception {

            // expecting
            expectedException.expect(InvalidException.class);
            expectedException.expectMessage("Check the 'are you sure' to continue");

            // when
            wrap(noteActionRemove).remove(note, false);
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
            wrap(noteActionRemove).remove(note, null);
        }
    }

    public static class DomainEventIntegTest extends NoteActionRemoveIntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class Subscriber extends AbstractSubscriber {

            NoteActionRemove.DomainEvent ev;

            @Subscribe
            public void on(NoteActionRemove.DomainEvent ev) {
                this.ev = ev;
            }
        }

        @Inject
        Subscriber subscriber;

        @Test
        public void fires_event() throws Exception {

            // when
            wrap(noteActionRemove).remove(note, true);

            // then
            assertThat(subscriber.ev).isNotNull();
            assertThat(subscriber.ev.getSource()).isSameAs(noteActionRemove);
            assertThat(subscriber.ev.getArguments().get(0)).isSameAs(note);
        }
    }

}