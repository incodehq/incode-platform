package org.incode.example.classification.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.classification.demo.usage.dom.classification.demowithatpath.ClassificationForSomeClassifiedObject;
import org.incode.example.classification.demo.usage.dom.classification.otherwithatpath.ClassificationForOtherClassifiedObject;

public class Classifications_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(ClassificationForOtherClassifiedObject.class);
        deleteFrom(ClassificationForSomeClassifiedObject.class);
    }

}
