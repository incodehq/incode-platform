package org.incode.examples.note.demo.shared.demo.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.note.demo.shared.demo.dom.NotableObject;

public class NotableObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(NotableObject.class);
    }

}
