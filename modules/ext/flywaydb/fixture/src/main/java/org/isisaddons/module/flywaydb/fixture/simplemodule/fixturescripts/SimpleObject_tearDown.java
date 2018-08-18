package org.isisaddons.module.flywaydb.fixture.simplemodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.flywaydb.fixture.simplemodule.dom.SimpleObject;

public class SimpleObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(SimpleObject.class);
    }

}
