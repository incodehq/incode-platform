package org.incode.example.alias.demo.examples.tags.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.alias.demo.examples.tags.dom.demo.DemoTaggableObject;
import org.incode.example.tags.dom.impl.Tag;

public class DemoTaggableObjects_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(DemoTaggableObject.class);
        deleteFrom(Tag.class);
    }

}
