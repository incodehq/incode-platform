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

import org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItem;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DeleteAllToDoItems extends DiscoverableFixtureScript {

    private final String user;

    public DeleteAllToDoItems() {
        this(null);
    }

    public DeleteAllToDoItems(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        isisJdoSupport.executeUpdate(String.format("delete from \"%s\" where \"ownedBy\" = '%s'", ExcelModuleDemoToDoItem.class.getSimpleName(), ownedBy));
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
