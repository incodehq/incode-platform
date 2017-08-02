package org.incode.domainapp.example.dom.wkt.excel.fixture;

import java.math.BigDecimal;
import org.incode.domainapp.example.dom.wkt.excel.dom.demo.ExcelWicketToDoItem;
import org.incode.domainapp.example.dom.wkt.excel.dom.demo.ExcelWicketToDoItem.Category;
import org.incode.domainapp.example.dom.wkt.excel.dom.demo.ExcelWicketToDoItem.Subcategory;
import org.incode.domainapp.example.dom.wkt.excel.dom.demo.ExcelWicketToDoItems;
import org.joda.time.LocalDate;
import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class ExcelWicketSetUpFixture extends DiscoverableFixtureScript {

    private final String user;

    public ExcelWicketSetUpFixture() {
        this(null);
    }
    
    public ExcelWicketSetUpFixture(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : getContainer().getUser().getName();

        execute(new ExcelWicketTearDownFixture(ownedBy), executionContext);

        installFor(ownedBy, executionContext);
        
        getContainer().flush();
    }

    private void installFor(String user, ExecutionContext executionContext) {

        ExcelWicketToDoItem t1 = createToDoItemForUser("Buy milk", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("0.75"), executionContext);
        ExcelWicketToDoItem t2 = createToDoItemForUser("Buy bread", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("1.75"), executionContext);
        ExcelWicketToDoItem t3 = createToDoItemForUser("Buy stamps", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("10.00"), executionContext);
        t3.setComplete(true);
        ExcelWicketToDoItem t4 = createToDoItemForUser("Pick up laundry", Category.Domestic, Subcategory.Chores, user, daysFromToday(6), new BigDecimal("7.50"), executionContext);
        ExcelWicketToDoItem t5 = createToDoItemForUser("Mow lawn", Category.Domestic, Subcategory.Garden, user, daysFromToday(6), null, executionContext);

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
        
        getContainer().flush();
    }


    // //////////////////////////////////////

    private ExcelWicketToDoItem createToDoItemForUser(final String description, final Category category, Subcategory subcategory, String user, final LocalDate dueBy, final BigDecimal cost, ExecutionContext executionContext) {
        final ExcelWicketToDoItem toDoItem = toDoItems.newToDo(description, category, subcategory, user, dueBy, cost);
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
    private ExcelWicketToDoItems toDoItems;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
