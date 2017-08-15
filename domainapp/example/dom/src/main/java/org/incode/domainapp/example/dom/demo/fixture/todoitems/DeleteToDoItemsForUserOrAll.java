package org.incode.domainapp.example.dom.demo.fixture.todoitems;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoToDoItem_tearDown;

public class DeleteToDoItemsForUserOrAll extends FixtureScript {

    private final String ownedBy;

    public DeleteToDoItemsForUserOrAll(String ownedBy) {
        this.ownedBy = ownedBy;
    }


    @Override
    protected void execute(ExecutionContext executionContext) {

        final FixtureScript fs =
                ownedBy != null
                        ? new DeleteToDoItemsForUser(ownedBy)
                        : new DemoToDoItem_tearDown();
        executionContext.executeChild(this, fs);
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
