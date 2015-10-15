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

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.note.Note;
import org.incode.module.note.dom.impl.note.Note_remove;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryForDemo;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;
import org.incode.module.note.integtests.NoteModuleIntegTest;

import static org.assertj.core.api.Assertions.assertThat;

public class Note_remove_IntegTest extends NoteModuleIntegTest {

    //region > injected services
    //endregion

    @Inject
    CalendarNameRepositoryForDemo calendarNameRepository;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    Notable notable;
    Note note;
    Note noteWithoutDate;
    Note noteWithoutText;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        notable = wrap(noteDemoObjectMenu).create("Foo");
        calendarNameRepository.setCalendarNames(NoteDemoObject.class, "BLUE", "GREEN", "RED");

        wrap(mixinAddNote(notable)).__("note A", fakeData.jodaLocalDates().any(), "GREEN");
        wrap(mixinAddNote(notable)).__("note B", null, null);
        wrap(mixinAddNote(notable)).__(null, fakeData.jodaLocalDates().any(), "RED");

        final List<Note> noteList = wrap(mixinNotes(notable)).__();
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


    public static class ActionImplementationIntegTest extends Note_remove_IntegTest {

        @Test
        public void happy_case() throws Exception {

            // given
            final List<Note> noteList = wrap(mixinNotes(notable)).__();
            assertThat(noteList).hasSize(3);

            // when
            wrap(mixinRemove(note)).__();

            // then
            final List<Note> noteListAfter = wrap(mixinNotes(notable)).__();
            assertThat(noteListAfter).hasSize(2);

        }
    }

    public static class DomainEventIntegTest extends Note_remove_IntegTest {

        @DomainService(nature = NatureOfService.DOMAIN)
        public static class Subscriber extends AbstractSubscriber {

            Note_remove.Event ev;

            @Subscribe
            public void on(Note_remove.Event ev) {
                this.ev = ev;
            }
        }

        @Inject
        Subscriber subscriber;

        @Test
        public void fires_event() throws Exception {

            // when
            final Note_remove mixinRemove = mixinRemove(note);
            wrap(mixinRemove).__();

            // then
            assertThat(subscriber.ev).isNotNull();
            assertThat(subscriber.ev.getSource()).isSameAs(mixinRemove);
            assertThat(subscriber.ev.getSource().getNote()).isSameAs(note);
        }
    }

}