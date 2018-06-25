package org.incode.domainapp.extended.module.fixtures.shared.simple.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.simple.dom.SimpleObject;

public class SimpleObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(SimpleObject.class);
    }

}
