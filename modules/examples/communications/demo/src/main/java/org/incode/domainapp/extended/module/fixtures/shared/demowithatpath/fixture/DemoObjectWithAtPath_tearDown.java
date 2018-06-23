package org.incode.examples.commchannel.demo.shared.demowithatpath.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.commchannel.demo.shared.demowithatpath.dom.DemoObjectWithAtPath;

public class DemoObjectWithAtPath_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithAtPath.class);
    }

}
