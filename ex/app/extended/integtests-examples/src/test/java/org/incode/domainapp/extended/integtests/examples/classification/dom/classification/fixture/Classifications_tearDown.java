package org.incode.domainapp.extended.integtests.examples.classification.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.integtests.examples.classification.dom.classification.dom.classification.demowithatpath.ClassificationForDemoObjectWithAtPath;
import org.incode.domainapp.extended.integtests.examples.classification.dom.classification.dom.classification.otherwithatpath.ClassificationForOtherObjectWithAtPath;

public class Classifications_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        deleteFrom(ClassificationForOtherObjectWithAtPath.class);
        deleteFrom(ClassificationForDemoObjectWithAtPath.class);


    }


}
