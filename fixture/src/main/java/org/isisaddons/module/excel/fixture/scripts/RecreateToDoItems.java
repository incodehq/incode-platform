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
package org.isisaddons.module.excel.fixture.scripts;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class RecreateToDoItems extends DiscoverableFixtureScript {

    private final String user;

    public RecreateToDoItems() {
        this(null);
    }

    public RecreateToDoItems(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {


        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        execute(new DeleteAllToDoItems(ownedBy), executionContext);
        execute(new CreateAllToDoItems(ownedBy), executionContext);

        getContainer().flush();
    }

}
