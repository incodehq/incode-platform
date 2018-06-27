package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.dom.dom.DemoToDoItem;

public class DemoToDoItem_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoToDoItem.class);
    }

}
