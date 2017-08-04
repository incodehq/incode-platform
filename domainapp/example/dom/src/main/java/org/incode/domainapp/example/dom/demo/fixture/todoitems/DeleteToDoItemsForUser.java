package org.incode.domainapp.example.dom.demo.fixture.todoitems;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DeleteToDoItemsForUser extends DiscoverableFixtureScript {

    private final String user;

    public DeleteToDoItemsForUser() {
        this(null);
    }

    public DeleteToDoItemsForUser(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : userService.getUser().getName();

        isisJdoSupport.executeUpdate(String.format(
                "delete "
                        + "from \"exampleDemo\".\"DemoToDoItemDependencies\" "
                        + "where \"dependingId\" IN "
                        + "(select \"id\" from \"exampleDemo\".\"DemoToDoItem\" where \"ownedBy\" = '%s') ",
                ownedBy));

        isisJdoSupport.executeUpdate(String.format(
                "delete from \"exampleDemo\".\"DemoToDoItem\" "
                        + "where \"ownedBy\" = '%s'", ownedBy));
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
