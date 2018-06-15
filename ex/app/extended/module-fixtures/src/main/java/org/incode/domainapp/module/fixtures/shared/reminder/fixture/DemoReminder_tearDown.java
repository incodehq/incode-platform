package org.incode.domainapp.module.fixtures.shared.reminder.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoReminder_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoReminder\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
