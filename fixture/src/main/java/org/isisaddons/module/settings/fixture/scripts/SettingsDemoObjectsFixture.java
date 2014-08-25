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

package org.isisaddons.module.settings.fixture.scripts;

import org.isisaddons.module.settings.fixture.dom.SettingsDemoObject;
import org.isisaddons.module.settings.fixture.dom.SettingsDemoObjects;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class SettingsDemoObjectsFixture extends DiscoverableFixtureScript {

    public SettingsDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new SettingsDemoObjectsTearDownFixture(), executionContext);

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private SettingsDemoObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, settingsDemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SettingsDemoObjects settingsDemoObjects;

}
