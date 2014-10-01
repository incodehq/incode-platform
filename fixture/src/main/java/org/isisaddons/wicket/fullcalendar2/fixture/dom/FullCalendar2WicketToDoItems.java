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
package org.isisaddons.wicket.fullcalendar2.fixture.dom;

import java.util.Collections;
import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(menuOrder = "10")
@Named("ToDos")
public class FullCalendar2WicketToDoItems {

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
    public List<FullCalendar2WicketToDoItem> notYetComplete() {
        final List<FullCalendar2WicketToDoItem> items = notYetCompleteNoUi();
        if(items.isEmpty()) {
            container.informUser("All to-do items have been completed :-)");
        }
        return items;
    }

    @Programmatic
    public List<FullCalendar2WicketToDoItem> notYetCompleteNoUi() {
        return container.allMatches(
                new QueryDefault<FullCalendar2WicketToDoItem>(FullCalendar2WicketToDoItem.class,
                        "todo_notYetComplete", 
                        "ownedBy", currentUserName()));
    }
    //endregion

    //region > complete (action)

    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "3")
    public List<FullCalendar2WicketToDoItem> complete() {
        final List<FullCalendar2WicketToDoItem> items = completeNoUi();
        if(items.isEmpty()) {
            container.informUser("No to-do items have yet been completed :-(");
        }
        return items;
    }

    @Programmatic
    public List<FullCalendar2WicketToDoItem> completeNoUi() {
        return container.allMatches(
            new QueryDefault<FullCalendar2WicketToDoItem>(FullCalendar2WicketToDoItem.class,
                    "todo_complete", 
                    "ownedBy", currentUserName()));
    }

    //endregion

    //region > newToDo (action)

    @MemberOrder(sequence = "40")
    public FullCalendar2WicketToDoItem newToDo(
            final @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") @Named("Description") String description) {
        final String ownedBy = currentUserName();
        return newToDo(description, ownedBy);
    }

    //endregion

    //region > allToDos (action)

    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "50")
    public List<FullCalendar2WicketToDoItem> allToDos() {
        final String currentUser = currentUserName();
        final List<FullCalendar2WicketToDoItem> items = container.allMatches(FullCalendar2WicketToDoItem.class, FullCalendar2WicketToDoItem.Predicates.thoseOwnedBy(currentUser));
        Collections.sort(items);
        if(items.isEmpty()) {
            container.warnUser("No to-do items found.");
        }
        return items;
    }

    //endregion

    //region > autoComplete (action)


    @Programmatic // not part of metamodel
    public List<FullCalendar2WicketToDoItem> autoComplete(final String description) {
        // the JDO implementation ...
        return container.allMatches(
                new QueryDefault<FullCalendar2WicketToDoItem>(FullCalendar2WicketToDoItem.class,
                        "todo_autoComplete", 
                        "ownedBy", currentUserName(), 
                        "description", description));
    }

    //endregion

    //region > helpers

    @Programmatic // for use by fixtures
    public FullCalendar2WicketToDoItem newToDo(
            final String description, 
            final String userName) {
        final FullCalendar2WicketToDoItem toDoItem = container.newTransientInstance(FullCalendar2WicketToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setOwnedBy(userName);

        container.persist(toDoItem);
        container.flush();

        return toDoItem;
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
