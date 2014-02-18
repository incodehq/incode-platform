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

package fixture.todo;

import dom.todo.ToDoItem;
import dom.todo.ToDoItems;

import org.apache.isis.applib.fixtures.AbstractFixture;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class ToDoItemsFixture extends AbstractFixture {

    private final String user;

    public ToDoItemsFixture() {
        this(null);
    }
    
    public ToDoItemsFixture(String ownedBy) {
        this.user = ownedBy;
    }
    
    @Override
    public void install() {

        final String ownedBy = this.user != null? this.user : getContainer().getUser().getName();
        
        isisJdoSupport.executeUpdate("delete from \"ToDoItemDependencies\" where \"dependingId\" IN (SELECT \"id\" from \"ToDoItem\" where \"ownedBy\" = '" + ownedBy + "')");
        isisJdoSupport.executeUpdate("delete from \"ToDoItemDependencies\" where \"dependentId\" IN (SELECT \"id\" from \"ToDoItem\" where \"ownedBy\" = '" + ownedBy + "')");
        isisJdoSupport.executeUpdate("delete from \"ToDoItem\" where \"ownedBy\" = '" + ownedBy + "'");

        installFor(ownedBy);
        
        getContainer().flush();
    }

    private void installFor(String user) {

        ToDoItem t1 = createToDoItemForUser("Buy bread", user);
        ToDoItem t2 = createToDoItemForUser("Buy milk", user);
        ToDoItem t3 = createToDoItemForUser("Buy stamps", user);
        ToDoItem t4 = createToDoItemForUser("Pick up laundry", user);
        createToDoItemForUser("Mow lawn", user);
        createToDoItemForUser("Vacuum house", user);
        createToDoItemForUser("Sharpen knives", user);
        
        createToDoItemForUser("Write to penpal", user);
        
        createToDoItemForUser("Write blog post", user);
        createToDoItemForUser("Organize brown bag", user);
        createToDoItemForUser("Submit conference session", user);
        createToDoItemForUser("Stage Isis release", user);

        // set up some dependencies
        t1.add(t2);
        t1.add(t3);
        t1.add(t4);
        
        getContainer().flush();
    }


    // //////////////////////////////////////

    private ToDoItem createToDoItemForUser(final String description, final String user) {
        return toDoItems.newToDo(description, user);
    }

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
