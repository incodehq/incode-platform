package org.incode.domainapp.extended.integtests.examples.classification.dom.classification.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

public class ClassificationAndIntegrationModule_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new Classifications_tearDown());

        executionContext.executeChild(this, new ClassificationModule_tearDown());


    }


}
