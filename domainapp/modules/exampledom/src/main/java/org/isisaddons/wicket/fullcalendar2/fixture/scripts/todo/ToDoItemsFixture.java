package org.isisaddons.wicket.fullcalendar2.fixture.scripts.todo;

import org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItem;
import org.isisaddons.wicket.fullcalendar2.fixture.dom.FullCalendar2WicketToDoItems;
import org.joda.time.LocalDate;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ToDoItemsFixture extends FixtureScript {

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = getContainer().getUser().getName();

        installFor(ownedBy, executionContext);

        getContainer().flush();
    }

    private void installFor(String user, ExecutionContext executionContext) {

        FullCalendar2WicketToDoItem t1 = createToDoItemForUser("Buy bread", user, FullCalendar2WicketToDoItem.Category.Domestic, executionContext);
        FullCalendar2WicketToDoItem t2 = createToDoItemForUser("Buy milk", user, FullCalendar2WicketToDoItem.Category.Domestic, executionContext);
        FullCalendar2WicketToDoItem t3 = createToDoItemForUser("Buy stamps", user, FullCalendar2WicketToDoItem.Category.Domestic, executionContext);
        FullCalendar2WicketToDoItem t4 = createToDoItemForUser("Pick up laundry", user, FullCalendar2WicketToDoItem.Category.Domestic, executionContext);
        createToDoItemForUser("Mow lawn", user, FullCalendar2WicketToDoItem.Category.Domestic, executionContext);
        createToDoItemForUser("Vacuum house", user, FullCalendar2WicketToDoItem.Category.Domestic, executionContext);
        createToDoItemForUser("Sharpen knives", user, FullCalendar2WicketToDoItem.Category.Domestic, executionContext);

        createToDoItemForUser("Write to penpal", user, FullCalendar2WicketToDoItem.Category.Other, executionContext);

        createToDoItemForUser("Write blog post", user, FullCalendar2WicketToDoItem.Category.Professional, executionContext);
        createToDoItemForUser("Organize brown bag", user, FullCalendar2WicketToDoItem.Category.Professional, executionContext);
        createToDoItemForUser("Submit conference session", user, FullCalendar2WicketToDoItem.Category.Professional, executionContext);
        createToDoItemForUser("Stage Isis release", user, FullCalendar2WicketToDoItem.Category.Professional, executionContext);

        // set up some dependencies
        t1.add(t2);
        t1.add(t3);
        t1.add(t4);

        getContainer().flush();
    }


    // //////////////////////////////////////

    private FullCalendar2WicketToDoItem createToDoItemForUser(
            final String description,
            final String user,
            final FullCalendar2WicketToDoItem.Category category,
            final ExecutionContext executionContext) {
        FullCalendar2WicketToDoItem toDo = toDoItems.newToDo(description, user);

        LocalDate today = clockService.now();
        toDo.setDueBy(today.plusDays(random(10)-2));
        toDo.setCategory(category);

        return executionContext.add(this, description, toDo);
    }

    private static int random(int n) {
        return (int) (Math.random() * n);
    }

    // //////////////////////////////////////
    // Injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private FullCalendar2WicketToDoItems toDoItems;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    private ClockService clockService;

}
