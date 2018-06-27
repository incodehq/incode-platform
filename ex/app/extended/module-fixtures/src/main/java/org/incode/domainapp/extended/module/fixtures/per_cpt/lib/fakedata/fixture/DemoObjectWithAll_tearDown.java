package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.dom.DemoObjectWithAll;

public class DemoObjectWithAll_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithAll.class);
    }

}
