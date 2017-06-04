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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.user.UserService;

import org.isisaddons.wicket.gmap3.cpt.applib.Location;

@DomainService(menuOrder = "10")
@DomainServiceLayout(
        named = "ToDos"
)
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

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Gmap3ToDoItem> notYetComplete() {
        final List<Gmap3ToDoItem> items = notYetCompleteNoUi();
        if(items.isEmpty()) {
            messageService.informUser("All to-do items have been completed :-)");
        }
        return items;
    }

    @Programmatic
    public List<Gmap3ToDoItem> notYetCompleteNoUi() {
        return repositoryService.allMatches(
                new QueryDefault<>(Gmap3ToDoItem.class,
                        "todo_notYetComplete", 
                        "ownedBy", currentUserName()));
    }

    //endregion

    //region > complete (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence = "3")
    public List<Gmap3ToDoItem> complete() {
        final List<Gmap3ToDoItem> items = completeNoUi();
        if(items.isEmpty()) {
            messageService.informUser("No to-do items have yet been completed :-(");
        }
        return items;
    }

    @Programmatic
    public List<Gmap3ToDoItem> completeNoUi() {
        return repositoryService.allMatches(
            new QueryDefault<>(Gmap3ToDoItem.class,
                    "todo_complete", 
                    "ownedBy", currentUserName()));
    }

    //endregion

    //region > newToDo (action)

    @MemberOrder(sequence = "40")
    public Gmap3ToDoItem newToDo(
            @ParameterLayout(named = "Description")
            @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*") final
            String description) {
        final String ownedBy = currentUserName();
        return newToDo(description, ownedBy);
    }

    //endregion

    //region > allToDos (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence = "50")
    public List<Gmap3ToDoItem> allToDos() {
        final String currentUser = currentUserName();
        final List<Gmap3ToDoItem> items = repositoryService.allMatches(Gmap3ToDoItem.class, Gmap3ToDoItem.Predicates.thoseOwnedBy(currentUser));
        Collections.sort(items);
        if(items.isEmpty()) {
            messageService.warnUser("No to-do items found.");
        }
        return items;
    }

    //endregion

    //region > autoComplete

    @Programmatic // not part of metamodel
    public List<Gmap3ToDoItem> autoComplete(final String description) {
        // the JDO implementation ...
        return repositoryService.allMatches(
                new QueryDefault<>(Gmap3ToDoItem.class,
                        "todo_autoComplete", 
                        "ownedBy", currentUserName(), 
                        "description", description));
    }

    //endregion

    //region > programmatic helpers

    @Programmatic // for use by fixtures
    public Gmap3ToDoItem newToDo(
            final String description, 
            final String userName) {
        final Gmap3ToDoItem toDoItem = repositoryService.instantiate(Gmap3ToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setOwnedBy(userName);

         toDoItem.setLocation(
            new Location(51.5172+random(-0.05, +0.05), 0.1182 + random(-0.05, +0.05)));
        
        repositoryService.persistAndFlush(toDoItem);

        return toDoItem;
    }
    
    private static double random(final double from, final double to) {
        return Math.random() * (to-from) + from;
    }

    private String currentUserName() {
        return userService.getUser().getName();
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    UserService userService;

    @javax.inject.Inject
    MessageService messageService;

    @SuppressWarnings("unused")
    @javax.inject.Inject
    ClockService clockService;

    //endregion

}
