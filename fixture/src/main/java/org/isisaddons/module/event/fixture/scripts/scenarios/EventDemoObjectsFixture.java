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
package org.isisaddons.module.event.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.module.event.fixture.dom.EventDemoObject;
import org.isisaddons.module.event.fixture.dom.EventDemoObjects;
import org.isisaddons.module.event.fixture.scripts.teardown.EventDemoObjectsTearDownFixture;

public class EventDemoObjectsFixture extends DiscoverableFixtureScript {

    public EventDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
	executionContext.executeChild(this, new EventDemoObjectsTearDownFixture());

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private EventDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, eventDemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private EventDemoObjects eventDemoObjects;

}
