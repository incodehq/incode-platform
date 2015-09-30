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
package org.incode.module.note.fixture.scripts.scenarios;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.note.dom.note.NoteContributionsOnNotable;
import org.incode.module.note.fixture.dom.calendarname.CalendarName;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryUsingEnum;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;

public class NoteDemoObjectsFixture extends DiscoverableFixtureScript {

    public NoteDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
	executionContext.executeChild(this, new NoteDemoObjectsTearDownFixture());

        final LocalDate now = clockService.now();

        final NoteDemoObject foo = create("Foo", executionContext);
        noteContributionsOnNotable.addNoteToCalendar(foo, "", now, CalendarName.BLUE.name());
        noteContributionsOnNotable.addNoteToCalendar(foo, "", now.plusDays(1), CalendarName.GREEN.name());
        noteContributionsOnNotable.addNoteToCalendar(foo, "", now.plusDays(2), CalendarName.RED.name());

        final NoteDemoObject bar = create("Bar", executionContext);
        noteContributionsOnNotable.addNoteToCalendar(bar, "Note #1", null, null);
        noteContributionsOnNotable.addNoteToCalendar(bar, "Note #2", now.plusDays(-1),
                CalendarName.RED.name());

        final NoteDemoObject baz = create("Baz", executionContext);
        noteContributionsOnNotable.addNoteToCalendar(baz, "", now.plusDays(1), CalendarName.RED.name());
    }

    // //////////////////////////////////////

    private NoteDemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(noteDemoObjectMenu).create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    NoteDemoObjectMenu noteDemoObjectMenu;
    @javax.inject.Inject
    ClockService clockService;
    @javax.inject.Inject
    NoteContributionsOnNotable noteContributionsOnNotable;
    @javax.inject.Inject
    CalendarNameRepositoryUsingEnum calendarRepositoryUsingCalendarName;
}
