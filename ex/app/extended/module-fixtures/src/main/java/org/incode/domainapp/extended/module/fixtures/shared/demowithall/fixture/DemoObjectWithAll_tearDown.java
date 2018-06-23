package org.incode.examples.commchannel.demo.shared.demowithall.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.examples.commchannel.demo.shared.demowithall.dom.DemoObjectWithAll;

public class DemoObjectWithAll_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithAll.class);
    }

}
