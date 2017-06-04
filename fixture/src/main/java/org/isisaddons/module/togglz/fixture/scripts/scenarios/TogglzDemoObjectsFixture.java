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
package org.isisaddons.module.togglz.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.module.togglz.fixture.dom.module.demo.TogglzDemoObject;
import org.isisaddons.module.togglz.fixture.dom.module.demo.TogglzDemoObjects;
import org.isisaddons.module.togglz.fixture.scripts.teardown.TogglzDemoObjectsTearDownFixture;

public class TogglzDemoObjectsFixture extends DiscoverableFixtureScript {

    public TogglzDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new TogglzDemoObjectsTearDownFixture());

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private TogglzDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, togglzDemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private TogglzDemoObjects togglzDemoObjects;

}
