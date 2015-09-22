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
package org.isisaddons.module.commchannel.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

import org.isisaddons.module.commchannel.fixture.dom.CalendarName;
import org.isisaddons.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.isisaddons.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.isisaddons.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;

public class CommChannelDemoObjectsFixture extends DiscoverableFixtureScript {

    public CommChannelDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
	executionContext.executeChild(this, new CommChannelDemoObjectsTearDownFixture());

        final CommChannelDemoObject foo = create("Foo", executionContext);
        foo.addEvent(CalendarName.BLUE, clockService.now());
        foo.addEvent(CalendarName.GREEN, clockService.now().plusDays(1));
        foo.addEvent(CalendarName.RED, clockService.now().plusDays(2));

        final CommChannelDemoObject bar = create("Bar", executionContext);
        bar.addEvent(CalendarName.GREEN, clockService.now());
        bar.addEvent(CalendarName.RED, clockService.now().plusDays(-1));

        final CommChannelDemoObject baz = create("Baz", executionContext);
        baz.addEvent(CalendarName.RED, clockService.now().plusDays(1));
    }

    // //////////////////////////////////////

    private CommChannelDemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(commChannelDemoObjectMenu).create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;
    @javax.inject.Inject
    ClockService clockService;

}
