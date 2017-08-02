package org.incode.domainapp.example.dom.wkt.wickedcharts.dom.demo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
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
public class WickedChartsWicketToDoItems {

    public WickedChartsWicketToDoItems() {
    }
    
    // //////////////////////////////////////
    // Identification in the UI
    // //////////////////////////////////////

    public String getId() {
        return "toDoItems";
    }

    public String iconName() {
        return "ToDoItem";
    }

    // //////////////////////////////////////
    // NotYetComplete (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<WickedChartsWicketToDoItem> notYetComplete() {
        final List<WickedChartsWicketToDoItem> items = notYetCompleteNoUi();
        if(items.isEmpty()) {
            container.informUser("All to-do items have been completed :-)");
        }
        return items;
    }

    @Programmatic
    public List<WickedChartsWicketToDoItem> notYetCompleteNoUi() {
        return container.allMatches(
                new QueryDefault<>(WickedChartsWicketToDoItem.class,
                        "todo_notYetComplete", 
                        "ownedBy", currentUserName()));
    }


    // //////////////////////////////////////
    // Complete (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence = "3")
    public List<WickedChartsWicketToDoItem> complete() {
        final List<WickedChartsWicketToDoItem> items = completeNoUi();
        if(items.isEmpty()) {
            container.informUser("No to-do items have yet been completed :-(");
        }
        return items;
    }

    @Programmatic
    public List<WickedChartsWicketToDoItem> completeNoUi() {
        return container.allMatches(
            new QueryDefault<>(WickedChartsWicketToDoItem.class,
                    "todo_complete", 
                    "ownedBy", currentUserName()));
    }


    // //////////////////////////////////////
    // NewToDo (action)
    // //////////////////////////////////////

    @MemberOrder(sequence = "40")
    public WickedChartsWicketToDoItem newToDo(
            @ParameterLayout(named="Description") @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
            final String description,
            @ParameterLayout(named="Category")
            final WickedChartsWicketToDoItem.Category category,
            @ParameterLayout(named="Subcategory")
            final WickedChartsWicketToDoItem.Subcategory subcategory,
            @ParameterLayout(named="Due by") @Parameter(optionality = Optionality.OPTIONAL)
            final LocalDate dueBy,
            @ParameterLayout(named="Cost") @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal cost,
            @ParameterLayout(named="Previous cost") @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal previousCost) {
        final String ownedBy = currentUserName();
        return newToDo(description, category, subcategory, ownedBy, dueBy, cost, previousCost);
    }
    public WickedChartsWicketToDoItem.Category default1NewToDo() {
        return WickedChartsWicketToDoItem.Category.Professional;
    }
    public WickedChartsWicketToDoItem.Subcategory default2NewToDo() {
        return WickedChartsWicketToDoItem.Category.Professional.subcategories().get(0);
    }
    public LocalDate default3NewToDo() {
        return clockService.now().plusDays(14);
    }
    public List<WickedChartsWicketToDoItem.Subcategory> choices2NewToDo(
            final String description, final WickedChartsWicketToDoItem.Category category) {
        return WickedChartsWicketToDoItem.Subcategory.listFor(category);
    }
    public String validateNewToDo(
            final String description, 
            final WickedChartsWicketToDoItem.Category category,
            final WickedChartsWicketToDoItem.Subcategory subcategory,
            final LocalDate dueBy, 
            final BigDecimal cost,
            final BigDecimal previousCost) {
        return WickedChartsWicketToDoItem.Subcategory.validate(category, subcategory);
    }

    // //////////////////////////////////////
    // AllToDos (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence = "50")
    public List<WickedChartsWicketToDoItem> allToDos() {
        final String currentUser = currentUserName();
        final List<WickedChartsWicketToDoItem> items = container.allMatches(WickedChartsWicketToDoItem.class, WickedChartsWicketToDoItem.Predicates.thoseOwnedBy(currentUser));
        Collections.sort(items);
        if(items.isEmpty()) {
            container.warnUser("No to-do items found.");
        }
        return items;
    }

    // //////////////////////////////////////
    // AutoComplete
    // //////////////////////////////////////

    @Programmatic // not part of metamodel
    public List<WickedChartsWicketToDoItem> autoComplete(final String description) {
        return container.allMatches(
                new QueryDefault<>(WickedChartsWicketToDoItem.class,
                        "todo_autoComplete", 
                        "ownedBy", currentUserName(), 
                        "description", description));
    }


    // //////////////////////////////////////
    // Programmatic Helpers
    // //////////////////////////////////////

    @Programmatic // for use by fixtures
    public WickedChartsWicketToDoItem newToDo(
            final String description, 
            final WickedChartsWicketToDoItem.Category category,
            final WickedChartsWicketToDoItem.Subcategory subcategory,
            final String userName, 
            final LocalDate dueBy, 
            final BigDecimal cost,
            final BigDecimal previousCost) {
        final WickedChartsWicketToDoItem toDoItem = container.newTransientInstance(WickedChartsWicketToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setCategory(category);
        toDoItem.setSubcategory(subcategory);
        toDoItem.setOwnedBy(userName);
        toDoItem.setDueBy(dueBy);
        toDoItem.setCost(cost);
        toDoItem.setPreviousCost(previousCost);

        container.persist(toDoItem);
        container.flush();

        return toDoItem;
    }
    
    private String currentUserName() {
        return container.getUser().getName();
    }

    
    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ClockService clockService;

}
