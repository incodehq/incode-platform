package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.tags.dom.demo.DemoTaggableObject;
import org.incode.example.tags.dom.impl.Tag;

public class DemoTaggableObjects_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(DemoTaggableObject.class);
        deleteFrom(Tag.class);
    }

}
