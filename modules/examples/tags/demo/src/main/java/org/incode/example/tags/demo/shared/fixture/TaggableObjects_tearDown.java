package org.incode.example.tags.demo.shared.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.tags.demo.shared.dom.demo.TaggableObject;
import org.incode.example.tags.dom.impl.Tag;

public class TaggableObjects_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(TaggableObject.class);
        deleteFrom(Tag.class);
    }

}
