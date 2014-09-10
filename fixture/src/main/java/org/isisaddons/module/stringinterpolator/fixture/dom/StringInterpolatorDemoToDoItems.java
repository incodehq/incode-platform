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
package org.isisaddons.module.stringinterpolator.fixture.dom;

import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@DomainService
@Named("ToDos")
public class StringInterpolatorDemoToDoItems {


    //region > identification

    public String getId() {
        return "toDoItems";
    }

    public String iconName() {
        return "ToDoItem";
    }

    //endregion

    //region > newToDo (action)

    @MemberOrder(sequence = "40")
    public StringInterpolatorDemoToDoItem newToDo(
            final @RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*") @Named("Description") String description,
            final @Named("Documentation page") String documentationPage) {

        final StringInterpolatorDemoToDoItem toDoItem = container.newTransientInstance(StringInterpolatorDemoToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setDocumentationPage(documentationPage);

        container.persist(toDoItem);
        container.flush();

        return toDoItem;
    }
    //endregion

    //region > allToDos (action)


    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "50")
    public List<StringInterpolatorDemoToDoItem> allToDos() {
        return container.allInstances(StringInterpolatorDemoToDoItem.class);
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private DomainObjectContainer container;

    //endregion

}
