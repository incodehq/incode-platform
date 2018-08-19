package org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.scripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.demoapp.demomodule.dom.DemoReminder;

public class DemoReminder_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoReminder.class);
    }

}
