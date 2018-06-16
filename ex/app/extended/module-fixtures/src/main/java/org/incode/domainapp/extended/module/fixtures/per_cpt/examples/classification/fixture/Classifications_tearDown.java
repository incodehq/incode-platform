package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.dom.classification.demowithatpath.ClassificationForDemoObjectWithAtPath;
import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.classification.dom.classification.otherwithatpath.ClassificationForOtherObjectWithAtPath;

public class Classifications_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(ClassificationForOtherObjectWithAtPath.class);
        deleteFrom(ClassificationForDemoObjectWithAtPath.class);
    }

}
