/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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

package org.isisaddons.metamodel.paraname8.fixture.scripts;

import org.isisaddons.metamodel.paraname8.fixture.dom.Paraname8DemoObject;
import org.isisaddons.metamodel.paraname8.fixture.dom.Paraname8DemoObjects;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class Paraname8DemoObjectsFixture extends DiscoverableFixtureScript {

    public Paraname8DemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new Paraname8DemoObjectsTearDownFixture(), executionContext);

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private Paraname8DemoObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, paraname8DemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Paraname8DemoObjects paraname8DemoObjects;

}
