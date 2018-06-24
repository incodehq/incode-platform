package org.incode.domainapp.extended.module.fixtures.shared.demo.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

public class DemoObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObject.class);
    }

}
