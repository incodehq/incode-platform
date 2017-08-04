package org.incode.domainapp.example.dom.demo.fixture.setup.todoitems;

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

        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        isisJdoSupport.executeUpdate(String.format("delete from \"exampleDemo\".\"DemoToDoItem\" where \"ownedBy\" = '%s'", ownedBy));
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
