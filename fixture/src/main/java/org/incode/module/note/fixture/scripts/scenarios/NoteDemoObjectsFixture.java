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

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.incode.module.note.fixture.dom.NoteDemoObject;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;

import org.incode.module.note.fixture.dom.CalendarName;
import org.incode.module.note.fixture.dom.NoteDemoObjectMenu;

public class NoteDemoObjectsFixture extends DiscoverableFixtureScript {

    public NoteDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
	executionContext.executeChild(this, new NoteDemoObjectsTearDownFixture());

        final NoteDemoObject foo = create("Foo", executionContext);
        foo.addEvent(CalendarName.BLUE, clockService.now());
        foo.addEvent(CalendarName.GREEN, clockService.now().plusDays(1));
        foo.addEvent(CalendarName.RED, clockService.now().plusDays(2));

        final NoteDemoObject bar = create("Bar", executionContext);
        bar.addEvent(CalendarName.GREEN, clockService.now());
        bar.addEvent(CalendarName.RED, clockService.now().plusDays(-1));

        final NoteDemoObject baz = create("Baz", executionContext);
        baz.addEvent(CalendarName.RED, clockService.now().plusDays(1));
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

}
