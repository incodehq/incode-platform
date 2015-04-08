/*
 *  Copyright 2014 Dan Haywood
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
package org.isisaddons.module.fakedata.fixture.scripts;

import org.isisaddons.module.fakedata.fixture.dom.FakeDataDemoObject;
import org.isisaddons.module.fakedata.fixture.dom.FakeDataDemoObjects;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class FakeDataDemoObjectsFixture extends DiscoverableFixtureScript {

    public FakeDataDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        execute(new FakeDataDemoObjectsTearDownFixture(), executionContext);

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private FakeDataDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, fakeDataDemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private FakeDataDemoObjects fakeDataDemoObjects;

}
