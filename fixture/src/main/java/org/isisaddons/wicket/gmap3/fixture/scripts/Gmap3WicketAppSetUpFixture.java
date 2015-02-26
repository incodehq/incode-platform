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

package org.isisaddons.wicket.gmap3.fixture.scripts;

import org.isisaddons.wicket.gmap3.fixture.scripts.todo.Gmap3WicketToDoItemsFixture;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class Gmap3WicketAppSetUpFixture extends DiscoverableFixtureScript {

    private final String ownedBy;

    public Gmap3WicketAppSetUpFixture() {
        this(null);
    }

    public Gmap3WicketAppSetUpFixture(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        execute(new Gmap3WicketAppTearDownFixture(ownedBy), executionContext);
        execute(new Gmap3WicketToDoItemsFixture(), executionContext);
    }

}
