package org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.scripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

public class DemoReminder_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoReminder.class);
    }

}
