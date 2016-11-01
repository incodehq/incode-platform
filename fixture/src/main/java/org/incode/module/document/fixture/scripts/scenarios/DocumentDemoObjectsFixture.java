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
package org.incode.module.document.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.module.document.fixture.scripts.teardown.DocumentDemoObjectsTearDownFixture;
import org.incode.module.document.fixture.dom.demo.DemoObject;
import org.incode.module.document.fixture.dom.demo.DemoObjectMenu;

public class DocumentDemoObjectsFixture extends DiscoverableFixtureScript {

    //region > injected services
    @javax.inject.Inject
    DemoObjectMenu demoObjectMenu;
    //endregion

    //region > constructor
    public DocumentDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new DocumentDemoObjectsTearDownFixture());

        final DemoObject foo = create("Foo", executionContext);
        final DemoObject bar = create("Bar", executionContext);
        final DemoObject baz = create("Baz", executionContext);
    }


    // //////////////////////////////////////

    private DemoObject create(
            final String name,
            final ExecutionContext executionContext) {

        return executionContext.addResult(this, wrap(demoObjectMenu).create(name));
    }


}
