package org.incode.domainapp.example.dom.demo.fixture.reminders;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoReminder_tearDown;

public class DemoReminder_recreate4 extends FixtureScript {


    @Override
    public void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DemoReminder_tearDown());
        ec.executeChild(this, new DemoReminder_create4());
    }


}
