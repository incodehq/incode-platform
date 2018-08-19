package org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.fixturescripts;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.dom.Category;
import org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.dom.Subcategory;
import org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.dom.WickedChartsDemoToDoItem;
import org.isisaddons.wicket.wickedcharts.fixture.demoapp.todomodule.dom.WickedChartsDemoToDoItemMenu;

public class WickedChartsDemoToDoItem_recreate5_for extends FixtureScript {

    private final String user;

    public WickedChartsDemoToDoItem_recreate5_for() {
        this(null);
    }
    
    public WickedChartsDemoToDoItem_recreate5_for(String ownedBy) {
        this.user = ownedBy;
    }


    @Override
    protected void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : userService.getUser().getName();

        executionContext.executeChild(this, new WickedChartsDemoToDoItem_tearDown2(ownedBy));

        installFor(ownedBy, executionContext);
        
        transactionService.flushTransaction();
    }

    private void installFor(String user, ExecutionContext executionContext) {

        WickedChartsDemoToDoItem t1 = createToDoItemForUser("Buy milk", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("0.75"), executionContext);
        WickedChartsDemoToDoItem t2 = createToDoItemForUser("Buy bread", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("1.75"), executionContext);
        WickedChartsDemoToDoItem t3 = createToDoItemForUser("Buy stamps", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("10.00"), executionContext);
        t3.setComplete(true);
        WickedChartsDemoToDoItem t4 = createToDoItemForUser("Pick up laundry", Category.Domestic, Subcategory.Chores, user, daysFromToday(6), new BigDecimal("7.50"), executionContext);
        WickedChartsDemoToDoItem t5 = createToDoItemForUser("Mow lawn", Category.Domestic, Subcategory.Garden, user, daysFromToday(6), null, executionContext);

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

    private WickedChartsDemoToDoItem createToDoItemForUser(final String description, final Category category, Subcategory subcategory, String user, final LocalDate dueBy, final BigDecimal cost, ExecutionContext executionContext) {
        final WickedChartsDemoToDoItem toDoItem = demoToDoItemMenu.newToDoItem(description, category, subcategory, user, dueBy, cost);
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
    private WickedChartsDemoToDoItemMenu demoToDoItemMenu;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
