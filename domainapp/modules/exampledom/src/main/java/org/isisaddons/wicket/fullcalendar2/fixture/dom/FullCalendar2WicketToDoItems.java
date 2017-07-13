package org.isisaddons.wicket.fullcalendar2.fixture.dom;

import java.util.Collections;
import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
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

@DomainService(menuOrder = "10")
@DomainServiceLayout(
        named = "ToDos"
)
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

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
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
                new QueryDefault<>(FullCalendar2WicketToDoItem.class,
                        "todo_notYetComplete", 
                        "ownedBy", currentUserName()));
    }
    //endregion

    //region > complete (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
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
            new QueryDefault<>(FullCalendar2WicketToDoItem.class,
                    "todo_complete", 
                    "ownedBy", currentUserName()));
    }

    //endregion

    //region > newToDo (action)

    @MemberOrder(sequence = "40")
    public FullCalendar2WicketToDoItem newToDo(
            @ParameterLayout(named = "Description")
            @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
            final String description) {
        final String ownedBy = currentUserName();
        return newToDo(description, ownedBy);
    }

    //endregion

    //region > allToDos (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
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
                new QueryDefault<>(FullCalendar2WicketToDoItem.class,
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
