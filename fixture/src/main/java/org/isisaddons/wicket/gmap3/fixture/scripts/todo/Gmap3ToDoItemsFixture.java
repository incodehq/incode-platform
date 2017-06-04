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

package org.isisaddons.wicket.gmap3.fixture.scripts.todo;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.isisaddons.wicket.gmap3.fixture.dom.Gmap3ToDoItem;
import org.isisaddons.wicket.gmap3.fixture.dom.Gmap3WicketToDoItems;

public class Gmap3ToDoItemsFixture extends FixtureScript {

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = getContainer().getUser().getName();

        installFor(ownedBy, executionContext);

        getContainer().flush();
    }

    private void installFor(String user, ExecutionContext executionContext) {

        Gmap3ToDoItem t1 = createToDoItemForUser("Buy bread", user, executionContext);
        Gmap3ToDoItem t2 = createToDoItemForUser("Buy milk", user, executionContext);
        Gmap3ToDoItem t3 = createToDoItemForUser("Buy stamps", user, executionContext);
        Gmap3ToDoItem t4 = createToDoItemForUser("Pick up laundry", user, executionContext);
        createToDoItemForUser("Mow lawn", user, executionContext);
        createToDoItemForUser("Vacuum house", user, executionContext);
        createToDoItemForUser("Sharpen knives", user, executionContext);
        
        createToDoItemForUser("Write to penpal", user, executionContext);
        
        createToDoItemForUser("Write blog post", user, executionContext);
        createToDoItemForUser("Organize brown bag", user, executionContext);
        createToDoItemForUser("Submit conference session", user, executionContext);
        createToDoItemForUser("Stage Isis release", user, executionContext);

        // set up some dependencies
        t1.add(t2);
        t1.add(t3);
        t1.add(t4);
        
        getContainer().flush();
    }

    private Gmap3ToDoItem createToDoItemForUser(final String description, final String user, ExecutionContext executionContext) {
        return executionContext.add(this, description, toDoItems.newToDo(description, user));
    }

    //region > injected services

    @javax.inject.Inject
    private Gmap3WicketToDoItems toDoItems;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;
    //endregion

}
