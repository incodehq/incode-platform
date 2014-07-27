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

package org.isisaddons.module.stringinterpolator.fixture.scripts;

import org.isisaddons.module.stringinterpolator.fixture.dom.ToDoItem;
import org.isisaddons.module.stringinterpolator.fixture.dom.ToDoItems;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class ToDoItemsFixture extends DiscoverableFixtureScript {

    private final String user;

    public ToDoItemsFixture() {
        this(null);
    }
    
    public ToDoItemsFixture(String ownedBy) {
        this.user = ownedBy;
    }
    
    @Override
    public void execute(ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from \"ToDoItem\"");

        installFor(executionContext);
        
        getContainer().flush();
    }

    private void installFor(ExecutionContext executionContext) {

        executionContext.add(this, createToDoItem("Documentation page - Review main Isis doc page", "documentation.html"));
        executionContext.add(this, createToDoItem("Screenshots - Review Isis screenshots", "intro/elevator-pitch/isis-in-pictures.html"));
        executionContext.add(this, createToDoItem("Lookup some Isis articles", "intro/learning-more/articles-and-presentations.html"));
        executionContext.add(this, createToDoItem("Learn about profiling in Isis", "reference/services/command-context.html"));

        getContainer().flush();
    }


    // //////////////////////////////////////

    private ToDoItem createToDoItem(final String description, final String documentationPage) {
        return toDoItems.newToDo(description, documentationPage);
    }


    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
