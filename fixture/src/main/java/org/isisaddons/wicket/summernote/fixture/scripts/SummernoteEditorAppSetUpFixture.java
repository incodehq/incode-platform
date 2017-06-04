/*
 *  Copyright 2013~2014 Dan Haywood
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
package org.isisaddons.wicket.summernote.fixture.scripts;

import org.isisaddons.wicket.summernote.fixture.scripts.todo.SummernoteEditorToDoItemsFixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class SummernoteEditorAppSetUpFixture extends DiscoverableFixtureScript {

    private final String user;

    public SummernoteEditorAppSetUpFixture() {
        this(null);
    }

    public SummernoteEditorAppSetUpFixture(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : getContainer().getUser().getName();

        // prereqs
        execute(new SummernoteEditorAppTearDownFixture(ownedBy), executionContext);

        // create
        execute(new SummernoteEditorToDoItemsFixture(), executionContext);
    }

}
