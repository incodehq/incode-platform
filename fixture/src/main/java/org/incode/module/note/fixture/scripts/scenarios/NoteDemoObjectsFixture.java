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

import org.incode.module.note.dom.api.notable.Notable;
import org.incode.module.note.dom.impl.note.Notable_addNote;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObject;
import org.incode.module.note.fixture.dom.notedemoobject.NoteDemoObjectMenu;
import org.incode.module.note.fixture.scripts.teardown.NoteDemoObjectsTearDownFixture;

public class NoteDemoObjectsFixture extends DiscoverableFixtureScript {

    //region > injected services
    @javax.inject.Inject
    NoteDemoObjectMenu noteDemoObjectMenu;
    @javax.inject.Inject
    ClockService clockService;
    //endregion

    //region > constructor
    public NoteDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }
    //endregion

    //region > mixins
    Notable_addNote mixinAddNote(final Notable notable) {
        return container.mixin(Notable_addNote.class, notable);
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new NoteDemoObjectsTearDownFixture());

        final LocalDate now = clockService.now();

        final NoteDemoObject foo = create("Foo", executionContext);
        wrap(mixinAddNote(foo)).$$("Note A", now, "BLUE");
        wrap(mixinAddNote(foo)).$$("Note B", now.plusDays(1), "GREEN");
        wrap(mixinAddNote(foo)).$$("Note C", now.plusDays(2), "RED");

        final NoteDemoObject bar = create("Bar", executionContext);
        wrap(mixinAddNote(bar)).$$("Note #1", null, null);
        wrap(mixinAddNote(bar)).$$("Note #2", now.plusDays(-1),
                "RED");

        final NoteDemoObject baz = create("Baz", executionContext);
        wrap(mixinAddNote(baz)).$$("Another note", now.plusDays(1), "RED");
    }


    // //////////////////////////////////////

    private NoteDemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(noteDemoObjectMenu).create(name));
    }


}
