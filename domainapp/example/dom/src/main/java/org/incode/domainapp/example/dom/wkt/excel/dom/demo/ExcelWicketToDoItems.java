package org.incode.domainapp.example.dom.wkt.excel.dom.demo;

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
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "exampleWktExcel.ToDoItems"
)
@DomainServiceLayout(
        named = "Excel (Wkt) ToDos",
        menuOrder = "10"
)
public class ExcelWicketToDoItems {

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
    public List<ExcelWicketToDoItem> notYetComplete() {
        final List<ExcelWicketToDoItem> items = notYetCompleteNoUi();
        if(items.isEmpty()) {
            container.informUser("All to-do items have been completed :-)");
        }
        return items;
    }

    @Programmatic
    public List<ExcelWicketToDoItem> notYetCompleteNoUi() {
        return container.allMatches(
                new QueryDefault<>(ExcelWicketToDoItem.class,
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
    public List<ExcelWicketToDoItem> complete() {
        final List<ExcelWicketToDoItem> items = completeNoUi();
        if(items.isEmpty()) {
            container.informUser("No to-do items have yet been completed :-(");
        }
        return items;
    }

    @Programmatic
    public List<ExcelWicketToDoItem> completeNoUi() {
        return container.allMatches(
            new QueryDefault<>(ExcelWicketToDoItem.class,
                    "todo_complete", 
                    "ownedBy", currentUserName()));
    }


    // //////////////////////////////////////
    // NewToDo (action)
    // //////////////////////////////////////

    @MemberOrder(sequence = "40")
    public ExcelWicketToDoItem newToDo(
            @ParameterLayout(named = "Description") @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
            final String description,
            @ParameterLayout(named = "Category")
            final ExcelWicketToDoItem.Category category,
            @ParameterLayout(named = "Subcategory")
            final ExcelWicketToDoItem.Subcategory subcategory,
            @ParameterLayout(named = "Due by") @Parameter(optionality = Optionality.OPTIONAL)
            final LocalDate dueBy,
            @ParameterLayout(named = "Cost") @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal cost) {
        final String ownedBy = currentUserName();
        return newToDo(description, category, subcategory, ownedBy, dueBy, cost);
    }
    public ExcelWicketToDoItem.Category default1NewToDo() {
        return ExcelWicketToDoItem.Category.Professional;
    }
    public ExcelWicketToDoItem.Subcategory default2NewToDo() {
        return ExcelWicketToDoItem.Category.Professional.subcategories().get(0);
    }
    public LocalDate default3NewToDo() {
        return clockService.now().plusDays(14);
    }
    public List<ExcelWicketToDoItem.Subcategory> choices2NewToDo(
            final String description, final ExcelWicketToDoItem.Category category) {
        return ExcelWicketToDoItem.Subcategory.listFor(category);
    }
    public String validateNewToDo(
            final String description, 
            final ExcelWicketToDoItem.Category category, final ExcelWicketToDoItem.Subcategory subcategory,
            final LocalDate dueBy, final BigDecimal cost) {
        return ExcelWicketToDoItem.Subcategory.validate(category, subcategory);
    }

    // //////////////////////////////////////
    // AllToDos (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence = "50")
    public List<ExcelWicketToDoItem> allToDos() {
        final String currentUser = currentUserName();
        final List<ExcelWicketToDoItem> items = container.allMatches(ExcelWicketToDoItem.class, ExcelWicketToDoItem.Predicates.thoseOwnedBy(currentUser));
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
    public List<ExcelWicketToDoItem> autoComplete(final String description) {
        return container.allMatches(
                new QueryDefault<>(ExcelWicketToDoItem.class,
                        "todo_autoComplete", 
                        "ownedBy", currentUserName(), 
                        "description", description));
    }


    // //////////////////////////////////////
    // Programmatic Helpers
    // //////////////////////////////////////

    @Programmatic // for use by fixtures
    public ExcelWicketToDoItem newToDo(
            final String description, 
            final ExcelWicketToDoItem.Category category,
            final ExcelWicketToDoItem.Subcategory subcategory,
            final String userName, 
            final LocalDate dueBy, final BigDecimal cost) {
        final ExcelWicketToDoItem toDoItem = container.newTransientInstance(ExcelWicketToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setCategory(category);
        toDoItem.setSubcategory(subcategory);
        toDoItem.setOwnedBy(userName);
        toDoItem.setDueBy(dueBy);
        toDoItem.setCost(cost);

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
