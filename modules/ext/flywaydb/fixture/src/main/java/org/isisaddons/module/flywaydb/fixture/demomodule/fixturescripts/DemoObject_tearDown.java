package org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.flywaydb.fixture.demomodule.dom.DemoObject;

public class DemoObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObject.class);
    }

}
