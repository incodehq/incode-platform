package domainapp.modules.exampledom.wicket.wickedcharts.fixture.data;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import org.apache.isis.applib.clock.Clock;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.wicket.wickedcharts.dom.demo.WickedChartsWicketToDoItem;
import domainapp.modules.exampledom.wicket.wickedcharts.dom.demo.WickedChartsWicketToDoItem.Category;
import domainapp.modules.exampledom.wicket.wickedcharts.dom.demo.WickedChartsWicketToDoItem.Subcategory;
import domainapp.modules.exampledom.wicket.wickedcharts.dom.demo.WickedChartsWicketToDoItems;

public class WickedChartsWicketToDoItemsFixture extends FixtureScript {

    private final String user;

    public WickedChartsWicketToDoItemsFixture() {
        this(null);
    }
    public WickedChartsWicketToDoItemsFixture(final String ownedBy) {
        this.user = ownedBy;
    }


    @Override
    protected void execute(final ExecutionContext executionContext) {

        final String ownedBy = this.user != null? this.user : getContainer().getUser().getName();
        
        installFor(ownedBy, executionContext);
        getContainer().flush();
    }

    private void installFor(final String user, final ExecutionContext executionContext) {

        createToDoItemForUser("Buy milk", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("0.75"), new BigDecimal("0.68"), executionContext);
        createToDoItemForUser("Buy bread", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("1.75"), new BigDecimal("1.70"), executionContext);
        createToDoItemForUser("Buy stamps", Category.Domestic, Subcategory.Shopping, user, daysFromToday(0), new BigDecimal("10.00"), new BigDecimal("9.75"), executionContext).setComplete(true);
        createToDoItemForUser("Pick up laundry", Category.Domestic, Subcategory.Chores, user, daysFromToday(6), new BigDecimal("7.50"), new BigDecimal("6.90"), executionContext);
        createToDoItemForUser("Mow lawn", Category.Domestic, Subcategory.Garden, user, daysFromToday(6), null, null, executionContext);
        createToDoItemForUser("Vacuum house", Category.Domestic, Subcategory.Housework, user, daysFromToday(3), null, null, executionContext);
        createToDoItemForUser("Sharpen knives", Category.Domestic, Subcategory.Chores, user, daysFromToday(14), null, null, executionContext);
        
        createToDoItemForUser("Write to penpal", Category.Other, Subcategory.Other, user, null, null, null, executionContext);
        
        createToDoItemForUser("Write blog post", Category.Professional, Subcategory.Marketing, user, daysFromToday(7), null, null, executionContext).setComplete(true);
        createToDoItemForUser("Organize brown bag", Category.Professional, Subcategory.Consulting, user, daysFromToday(14), null, null, executionContext);
        createToDoItemForUser("Submit conference session", Category.Professional, Subcategory.Education, user, daysFromToday(21), null, null, executionContext);
        createToDoItemForUser("Stage Isis release", Category.Professional, Subcategory.OpenSource, user, null, null, null, executionContext);

        getContainer().flush();
    }


    // //////////////////////////////////////

    private WickedChartsWicketToDoItem createToDoItemForUser(
            final String description,
            final Category category, Subcategory subcategory,
            final String user, final LocalDate dueBy,
            final BigDecimal cost, BigDecimal previousCost,
            final ExecutionContext executionContext) {
        return executionContext.add(this, description, toDoItems.newToDo(description, category, subcategory, user, dueBy, cost, previousCost));
    }

    private static LocalDate daysFromToday(final int i) {
        final LocalDate date = new LocalDate(Clock.getTimeAsDateTime());
        return date.plusDays(i);
    }


    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private WickedChartsWicketToDoItems toDoItems;

}
