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
package org.isisaddons.wicket.gmap3.fixture.dom;

import java.util.Collections;
import java.util.List;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(menuOrder = "10")
@Named("ToDos")
public class Gmap3WicketToDoItems {


    //region > identification in the UI

    public String getId() {
        return "toDoItems";
    }

    public String iconName() {
        return "ToDoItem";
    }

    //endregion

    //region > notYetComplete (action)

    @Bookmarkable
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "1")
    public List<Gmap3WicketToDoItem> notYetComplete() {
        final List<Gmap3WicketToDoItem> items = notYetCompleteNoUi();
        if(items.isEmpty()) {
            container.informUser("All to-do items have been completed :-)");
        }
        return items;
    }

    @Programmatic
    public List<Gmap3WicketToDoItem> notYetCompleteNoUi() {
        return container.allMatches(
                new QueryDefault<Gmap3WicketToDoItem>(Gmap3WicketToDoItem.class,
                        "todo_notYetComplete", 
                        "ownedBy", currentUserName()));
    }

    //endregion

    //region > complete (action)

    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "3")
    public List<Gmap3WicketToDoItem> complete() {
        final List<Gmap3WicketToDoItem> items = completeNoUi();
        if(items.isEmpty()) {
            container.informUser("No to-do items have yet been completed :-(");
        }
        return items;
    }

    @Programmatic
    public List<Gmap3WicketToDoItem> completeNoUi() {
        return container.allMatches(
            new QueryDefault<Gmap3WicketToDoItem>(Gmap3WicketToDoItem.class,
                    "todo_complete", 
                    "ownedBy", currentUserName()));
    }

    //endregion

    //region > newToDo (action)

    @MemberOrder(sequence = "40")
    public Gmap3WicketToDoItem newToDo(
            final @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") @Named("Description") String description) {
        final String ownedBy = currentUserName();
        return newToDo(description, ownedBy);
    }

    //endregion

    //region > allToDos (action)

    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "50")
    public List<Gmap3WicketToDoItem> allToDos() {
        final String currentUser = currentUserName();
        final List<Gmap3WicketToDoItem> items = container.allMatches(Gmap3WicketToDoItem.class, Gmap3WicketToDoItem.Predicates.thoseOwnedBy(currentUser));
        Collections.sort(items);
        if(items.isEmpty()) {
            container.warnUser("No to-do items found.");
        }
        return items;
    }

    //endregion

    //region > autoComplete

    @Programmatic // not part of metamodel
    public List<Gmap3WicketToDoItem> autoComplete(final String description) {
        // the JDO implementation ...
        return container.allMatches(
                new QueryDefault<Gmap3WicketToDoItem>(Gmap3WicketToDoItem.class,
                        "todo_autoComplete", 
                        "ownedBy", currentUserName(), 
                        "description", description));
    }

    //endregion

    //region > programmatic helpers

    @Programmatic // for use by fixtures
    public Gmap3WicketToDoItem newToDo(
            final String description, 
            final String userName) {
        final Gmap3WicketToDoItem toDoItem = container.newTransientInstance(Gmap3WicketToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setOwnedBy(userName);

         toDoItem.setLocation(
            new Location(51.5172+random(-0.05, +0.05), 0.1182 + random(-0.05, +0.05)));
        
        container.persist(toDoItem);
        container.flush();

        return toDoItem;
    }
    
    private static double random(double from, double to) {
        return Math.random() * (to-from) + from;
    }

    private String currentUserName() {
        return container.getUser().getName();
    }

    //endregion

    //region > injected services


    @javax.inject.Inject
    private DomainObjectContainer container;

    @SuppressWarnings("unused")
    @javax.inject.Inject
    private ClockService clockService;

    //endregion

}
