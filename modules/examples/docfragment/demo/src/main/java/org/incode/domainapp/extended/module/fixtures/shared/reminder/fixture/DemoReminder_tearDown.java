package org.incode.examples.commchannel.demo.shared.reminder.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.examples.commchannel.demo.shared.reminder.dom.DemoReminder;

public class DemoReminder_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoReminder.class);
    }

}
