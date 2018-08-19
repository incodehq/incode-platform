package org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.flywaydb.fixture.demomodule.dom.FlywayDbDemoObject;

public class FlywayDbDemoObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(FlywayDbDemoObject.class);
    }

}
