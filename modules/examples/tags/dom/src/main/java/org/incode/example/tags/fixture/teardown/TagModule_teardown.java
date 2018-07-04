package org.incode.example.tags.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.tags.dom.impl.Tag;

public class TagModule_teardown extends TeardownFixtureAbstract2 {
    @Override protected void execute(final ExecutionContext executionContext) {
        deleteFrom(Tag.class);
    }
}
