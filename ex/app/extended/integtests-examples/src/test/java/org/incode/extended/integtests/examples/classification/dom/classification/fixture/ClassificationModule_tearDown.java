package org.incode.extended.integtests.examples.classification.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.classification.dom.impl.applicability.Applicability;
import org.incode.example.classification.dom.impl.category.Category;
import org.incode.example.classification.dom.impl.classification.Classification;
import org.incode.extended.integtests.examples.classification.dom.classification.dom.classification.demowithatpath.ClassificationForDemoObjectWithAtPath;
import org.incode.extended.integtests.examples.classification.dom.classification.dom.classification.otherwithatpath.ClassificationForOtherObjectWithAtPath;

public class ClassificationModule_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // classifications
        deleteFrom(ClassificationForOtherObjectWithAtPath.class);
        deleteFrom(ClassificationForDemoObjectWithAtPath.class);


        // classification refdata
        deleteFrom(Classification.class);
        deleteFrom(Applicability.class);
        deleteFrom(Category.class);

    }


}
