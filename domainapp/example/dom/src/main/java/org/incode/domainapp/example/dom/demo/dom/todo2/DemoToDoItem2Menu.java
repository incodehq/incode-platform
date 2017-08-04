package org.incode.domainapp.example.dom.demo.dom.todo2;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleDemo.DemoToDoItem2Menu"
)
@DomainServiceLayout(
        menuOrder = "10",
        named = "Demo ToDoItem2s"
)
public class DemoToDoItem2Menu {



    @MemberOrder(sequence = "40")
    public DemoToDoItem2 newToDo(
            @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
            final String description,
            final String documentationPage) {

        final DemoToDoItem2 toDoItem = new DemoToDoItem2(description, documentationPage);

        container.persist(toDoItem);
        container.flush();

        return toDoItem;
    }


    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "50")
    public List<DemoToDoItem2> allToDos() {
        return container.allInstances(DemoToDoItem2.class);
    }


    @javax.inject.Inject
    DomainObjectContainer container;

}
