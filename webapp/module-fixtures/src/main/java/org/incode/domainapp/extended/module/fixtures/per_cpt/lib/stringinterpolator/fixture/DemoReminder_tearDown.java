package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.dom.DemoReminder;

public class DemoReminder_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoReminder.class);
    }

}
