package org.incode.example.classification.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.classification.demo.usage.dom.classification.demowithatpath.ClassificationForDemoObjectWithAtPath;
import org.incode.example.classification.demo.usage.dom.classification.otherwithatpath.ClassificationForOtherObjectWithAtPath;

public class Classifications_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(ClassificationForOtherObjectWithAtPath.class);
        deleteFrom(ClassificationForDemoObjectWithAtPath.class);
    }

}
