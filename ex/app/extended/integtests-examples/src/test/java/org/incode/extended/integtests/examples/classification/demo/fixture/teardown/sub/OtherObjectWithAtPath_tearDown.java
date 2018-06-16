package org.incode.extended.integtests.examples.classification.demo.fixture.teardown.sub;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.extended.integtests.examples.classification.demo.dom.otherwithatpath.OtherObjectWithAtPath;

public class OtherObjectWithAtPath_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(OtherObjectWithAtPath.class);
    }


}
