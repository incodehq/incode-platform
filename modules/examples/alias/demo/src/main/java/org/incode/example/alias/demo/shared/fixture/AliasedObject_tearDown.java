package org.incode.example.alias.demo.shared.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.alias.demo.shared.dom.AliasDemoObject;

public class AliasDemoObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(AliasDemoObject.class);
    }

}
