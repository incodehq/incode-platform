package org.incode.domainapp.example.dom.demo.fixture.todoitems;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.dom.todo.Category;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItem;
import org.incode.domainapp.example.dom.demo.dom.todo.DemoToDoItemMenu;
import org.incode.domainapp.example.dom.demo.dom.todo.Subcategory;

public class DemoToDoItem_recreate5_for extends FixtureScript {

    private final String user;

    public DemoToDoItem_recreate5_for() {
        this(null);
    }
    
    public DemoToDoItem_recreate5_for(String ownedBy) {
        this.user = ownedBy;
    }


    @Override
    protected void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : userService.getUser().getName();

        executionContext.executeChild(this, new DemoToDoItem_tearDown(ownedBy));

        installFor(ownedBy, executionContext);
        
        transactionService.flushTransaction();
    }

    private void installFor(String user, ExecutionContext executionContext) {

        DemoToDoItem t1 = createToDoItemForUser("Buy milk", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("0.75"), executionContext);
        DemoToDoItem t2 = createToDoItemForUser("Buy bread", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("1.75"), executionContext);
        DemoToDoItem t3 = createToDoItemForUser("Buy stamps", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("10.00"), executionContext);
        t3.setComplete(true);
        DemoToDoItem t4 = createToDoItemForUser("Pick up laundry", Category.Domestic, Subcategory.Chores, user, daysFromToday(6), new BigDecimal("7.50"), executionContext);
        DemoToDoItem t5 = createToDoItemForUser("Mow lawn", Category.Domestic, Subcategory.Garden, user, daysFromToday(6), null, executionContext);

        createToDoItemForUser("Vacuum house", Category.Domestic, Subcategory.Housework, user, daysFromToday(3), null, executionContext);
        createToDoItemForUser("Sharpen knives", Category.Domestic, Subcategory.Chores, user, daysFromToday(14), null, executionContext);
        
        createToDoItemForUser("Write to penpal", Category.Other, Subcategory.Other, user, null, null, executionContext);
        
        createToDoItemForUser("Write blog post", Category.Professional, Subcategory.Marketing, user, daysFromToday(7), null, executionContext).setComplete(true);
        createToDoItemForUser("Organize brown bag", Category.Professional, Subcategory.Consulting, user, daysFromToday(14), null, executionContext);
        createToDoItemForUser("Submit conference session", Category.Professional, Subcategory.Education, user, daysFromToday(21), null, executionContext);
        createToDoItemForUser("Stage Isis release", Category.Professional, Subcategory.OpenSource, user, null, null, executionContext);

        t1.add(t2);
        t1.add(t3);
        t1.add(t4);
        t1.add(t5);
        
        t2.add(t3);
        t2.add(t4);
        t2.add(t5);
        
        t3.add(t4);

        transactionService.flushTransaction();
    }


    // //////////////////////////////////////

    private DemoToDoItem createToDoItemForUser(final String description, final Category category, Subcategory subcategory, String user, final LocalDate dueBy, final BigDecimal cost, ExecutionContext executionContext) {
        final DemoToDoItem toDoItem = demoToDoItemMenu.newToDoItem(description, category, subcategory, user, dueBy, cost);
        executionContext.add(this, toDoItem);
        return toDoItem;
    }

    private static LocalDate daysFromToday(final int i) {
        final LocalDate date = new LocalDate(Clock.getTimeAsDateTime());
        return date.plusDays(i);
    }


    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DemoToDoItemMenu demoToDoItemMenu;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
