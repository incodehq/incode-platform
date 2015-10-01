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

import org.incode.module.note.dom.impl.note.NoteContributionsOnNotable;
import org.incode.module.note.fixture.dom.calendarname.CalendarNameRepositoryForDemo;
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
        wrap(noteContributionsOnNotable).addNote(foo, "Note A", now, "BLUE");
        wrap(noteContributionsOnNotable).addNote(foo, "Note B", now.plusDays(1), "GREEN");
        wrap(noteContributionsOnNotable).addNote(foo, "Note C", now.plusDays(2), "RED");

        final NoteDemoObject bar = create("Bar", executionContext);
        wrap(noteContributionsOnNotable).addNote(bar, "Note #1", null, null);
        wrap(noteContributionsOnNotable).addNote(bar, "Note #2", now.plusDays(-1),
                "RED");

        final NoteDemoObject baz = create("Baz", executionContext);
        wrap(noteContributionsOnNotable).addNote(baz, "Another note", now.plusDays(1), "RED");
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
    CalendarNameRepositoryForDemo calendarRepositoryUsingCalendarName;
}
