package domainapp.modules.exampledom.lib.excel.dom.demo;

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

import domainapp.modules.exampledom.lib.excel.dom.demo.ExcelModuleDemoToDoItem;
import domainapp.modules.exampledom.lib.excel.dom.demo.ExcelModuleDemoToDoItem.Category;
import domainapp.modules.exampledom.lib.excel.dom.demo.ExcelModuleDemoToDoItem.Subcategory;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "org.isisaddons.module.excel.fixture.dom.ExcelModuleDemoToDoItems"
)
@DomainServiceLayout(
        named = "ToDos"
)
public class ExcelModuleDemoToDoItems {

    public ExcelModuleDemoToDoItems() {
    }
    
    // //////////////////////////////////////
    // Identification in the UI
    // //////////////////////////////////////

    public String getId() {
        return "toDoItems";
    }

    public String iconName() {
        return "ExcelModuleDemoToDoItem";
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
    public List<ExcelModuleDemoToDoItem> notYetComplete() {
        final List<ExcelModuleDemoToDoItem> items = notYetCompleteNoUi();
        if(items.isEmpty()) {
            container.informUser("All to-do items have been completed :-)");
        }
        return items;
    }

    @Programmatic
    public List<ExcelModuleDemoToDoItem> notYetCompleteNoUi() {
        return container.allMatches(
                new QueryDefault<>(ExcelModuleDemoToDoItem.class,
                        "todo_notYetComplete", 
                        "ownedBy", currentUserName()));
    }
    
    
    // //////////////////////////////////////
    // findByDescription (action)
    // //////////////////////////////////////

    @Programmatic
    public ExcelModuleDemoToDoItem findByDescription(final String description) {
        return container.firstMatch(
                new QueryDefault<>(ExcelModuleDemoToDoItem.class,
                        "findByDescription",
                        "description", description,
                        "ownedBy", currentUserName()));
    }

    // //////////////////////////////////////
    // Complete (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence = "3")
    public List<ExcelModuleDemoToDoItem> complete() {
        final List<ExcelModuleDemoToDoItem> items = completeNoUi();
        if(items.isEmpty()) {
            container.informUser("No to-do items have yet been completed :-(");
        }
        return items;
    }

    @Programmatic
    public List<ExcelModuleDemoToDoItem> completeNoUi() {
        return container.allMatches(
            new QueryDefault<>(ExcelModuleDemoToDoItem.class,
                    "todo_complete", 
                    "ownedBy", currentUserName()));
    }


    // //////////////////////////////////////
    // NewToDo (action)
    // //////////////////////////////////////

    @MemberOrder(sequence = "40")
    public ExcelModuleDemoToDoItem newToDo(
            @ParameterLayout(named="Description") @Parameter(regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*")
            final String description,
            @ParameterLayout(named="Category")
            final Category category,
            @ParameterLayout(named="Subcategory")
            final Subcategory subcategory,
            @ParameterLayout(named="Due by") @Parameter(optionality = Optionality.OPTIONAL)
            final LocalDate dueBy,
            @ParameterLayout(named="Cost") @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal cost) {
        final String ownedBy = currentUserName();
        return newToDo(description, category, subcategory, ownedBy, dueBy, cost);
    }
    public Category default1NewToDo() {
        return Category.Professional;
    }
    public Subcategory default2NewToDo() {
        return Category.Professional.subcategories().get(0);
    }
    public LocalDate default3NewToDo() {
        return clockService.now().plusDays(14);
    }
    public List<Subcategory> choices2NewToDo(
            final String description, final Category category) {
        return Subcategory.listFor(category);
    }
    public String validateNewToDo(
            final String description, 
            final Category category, final Subcategory subcategory, 
            final LocalDate dueBy, final BigDecimal cost) {
        return Subcategory.validate(category, subcategory);
    }

    // //////////////////////////////////////
    // AllToDos (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence = "50")
    public List<ExcelModuleDemoToDoItem> allMyToDos() {
        final String currentUser = currentUserName();
        final List<ExcelModuleDemoToDoItem> items = container.allMatches(ExcelModuleDemoToDoItem.class, ExcelModuleDemoToDoItem.Predicates.thoseOwnedBy(currentUser));
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
    public List<ExcelModuleDemoToDoItem> autoComplete(final String description) {
        return container.allMatches(
                new QueryDefault<>(ExcelModuleDemoToDoItem.class,
                        "todo_autoComplete", 
                        "ownedBy", currentUserName(), 
                        "description", description));
    }


    // //////////////////////////////////////
    // Programmatic Helpers
    // //////////////////////////////////////

    @Programmatic // for use by fixtures
    public ExcelModuleDemoToDoItem newToDo(
            final String description, 
            final Category category, 
            final Subcategory subcategory,
            final String userName, 
            final LocalDate dueBy, final BigDecimal cost) {
        final ExcelModuleDemoToDoItem toDoItem = container.newTransientInstance(ExcelModuleDemoToDoItem.class);
        toDoItem.setDescription(description);
        toDoItem.setCategory(category);
        toDoItem.setSubcategory(subcategory);
        toDoItem.setOwnedBy(userName);
        toDoItem.setDueBy(dueBy);
        toDoItem.setCost(cost);

        container.persist(toDoItem);
        //container.flush();

        return toDoItem;
    }

    @Programmatic
    public List<ExcelModuleDemoToDoItem> allInstances() {
        return container.allInstances(ExcelModuleDemoToDoItem.class);
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
