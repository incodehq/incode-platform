package org.isisaddons.wicket.gmap3.fixture.scripts.todo;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.isisaddons.wicket.gmap3.fixture.dom.Gmap3ToDoItem;
import org.isisaddons.wicket.gmap3.fixture.dom.Gmap3WicketToDoItems;

public class Gmap3ToDoItemsFixture extends FixtureScript {

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = getContainer().getUser().getName();

        installFor(ownedBy, executionContext);

        getContainer().flush();
    }

    private void installFor(String user, ExecutionContext executionContext) {

        Gmap3ToDoItem t1 = createToDoItemForUser("Buy bread", user, executionContext);
        Gmap3ToDoItem t2 = createToDoItemForUser("Buy milk", user, executionContext);
        Gmap3ToDoItem t3 = createToDoItemForUser("Buy stamps", user, executionContext);
        Gmap3ToDoItem t4 = createToDoItemForUser("Pick up laundry", user, executionContext);
        createToDoItemForUser("Mow lawn", user, executionContext);
        createToDoItemForUser("Vacuum house", user, executionContext);
        createToDoItemForUser("Sharpen knives", user, executionContext);
        
        createToDoItemForUser("Write to penpal", user, executionContext);
        
        createToDoItemForUser("Write blog post", user, executionContext);
        createToDoItemForUser("Organize brown bag", user, executionContext);
        createToDoItemForUser("Submit conference session", user, executionContext);
        createToDoItemForUser("Stage Isis release", user, executionContext);

        // set up some dependencies
        t1.add(t2);
        t1.add(t3);
        t1.add(t4);
        
        getContainer().flush();
    }

    private Gmap3ToDoItem createToDoItemForUser(final String description, final String user, ExecutionContext executionContext) {
        return executionContext.add(this, description, toDoItems.newToDo(description, user));
    }

    //region > injected services

    @javax.inject.Inject
    private Gmap3WicketToDoItems toDoItems;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;
    //endregion

}
