package org.isisaddons.module.fakedata.fixture.demoapp.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.fakedata.fixture.demoapp.demomodule.dom.DemoObjectWithAll;

public class DemoObjectWithAll_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithAll.class);
    }

}
