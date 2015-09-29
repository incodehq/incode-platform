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
package org.incode.module.note.integtests;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.note.dom.NoteContributions;
import org.incode.module.note.fixture.dom.CalendarName;
import org.incode.module.note.fixture.dom.NoteDemoObject;
import org.incode.module.note.fixture.dom.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteDemoObjectTest extends NoteModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    NoteDemoObjectMenu noteDemoObjectMenu;

    @Inject
    NoteContributions noteContributions;

    NoteDemoObject noteDemoObject;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new NoteDemoObjectsTearDownFixture(), null);

        noteDemoObject = wrap(noteDemoObjectMenu).create("Foo");
    }


    public static class AddNote extends NoteDemoObjectTest {

        @Test
        public void canAdd() throws Exception {

            // given
            assertThat(noteContributions.events(noteDemoObject)).isEmpty();

            // when
            final LocalDate now = LocalDate.now();
            noteDemoObject.addEvent(CalendarName.BLUE, now);

            // then
            assertThat(noteContributions.events(noteDemoObject)).hasSize(1);

        }

        @Test
        public void canAddTwo() throws Exception {

            // given
            assertThat(noteContributions.events(noteDemoObject)).isEmpty();
            final LocalDate now = LocalDate.now();
            noteDemoObject.addEvent(CalendarName.BLUE, now);

            // when
            noteDemoObject.addEvent(CalendarName.GREEN, now);

            // then
            assertThat(noteContributions.events(noteDemoObject)).hasSize(2);

        }

        /**
         * This is really testing the demo object, rather than the event module...
         */
        @Test
        public void cannotAddToCalendarMoreThanOnce() throws Exception {

            // given
            assertThat(noteContributions.events(noteDemoObject)).isEmpty();
            final LocalDate now = LocalDate.now();
            noteDemoObject.addEvent(CalendarName.BLUE, now);
            noteDemoObject.addEvent(CalendarName.GREEN, now);

            // when
            final List<CalendarName> calendarNames = noteDemoObject.choices0AddEvent();

            // then
            assertThat(calendarNames).hasSize(1);
            assertThat(calendarNames.get(0)).isEqualTo(CalendarName.RED);
        }

    }

}