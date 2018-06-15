package org.incode.domainapp.extended.module.fixtures.shared.reminder.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class DemoReminder_recreate4 extends FixtureScript {


    @Override
    public void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoReminder_tearDown());
        ec.executeChild(this, new DemoReminder_create4());
    }


}
