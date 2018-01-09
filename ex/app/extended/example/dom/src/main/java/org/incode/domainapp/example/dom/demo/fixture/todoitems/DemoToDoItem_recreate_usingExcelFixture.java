package org.incode.domainapp.example.dom.demo.fixture.todoitems;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoToDoItem_recreate_usingExcelFixture extends FixtureScript {

    private final String user;

    public DemoToDoItem_recreate_usingExcelFixture() {
        this(null);
    }

    public DemoToDoItem_recreate_usingExcelFixture(String ownedBy) {
        this.user = ownedBy;
    }

    @Override
    public void execute(ExecutionContext executionContext) {

        final String ownedBy = this.user != null ? this.user : getContainer().getUser().getName();

        executionContext.executeChild(this, new DemoToDoItem_tearDown(ownedBy));
        executionContext.executeChild(this, new DemoToDoItem_create_usingExcelFixture(ownedBy));

        getContainer().flush();
    }

}
