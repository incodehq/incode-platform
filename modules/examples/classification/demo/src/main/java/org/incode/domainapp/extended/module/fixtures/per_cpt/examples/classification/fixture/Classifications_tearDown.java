package org.incode.example.alias.demo.examples.classification.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.alias.demo.examples.classification.dom.classification.demowithatpath.ClassificationForDemoObjectWithAtPath;
import org.incode.example.alias.demo.examples.classification.dom.classification.otherwithatpath.ClassificationForOtherObjectWithAtPath;

public class Classifications_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(ClassificationForOtherObjectWithAtPath.class);
        deleteFrom(ClassificationForDemoObjectWithAtPath.class);
    }

}
