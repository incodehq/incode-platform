package org.incode.domainapp.extended.module.fixtures.shared.todo.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoToDoItem_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoToDoItemDependencies\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoToDoItem\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
