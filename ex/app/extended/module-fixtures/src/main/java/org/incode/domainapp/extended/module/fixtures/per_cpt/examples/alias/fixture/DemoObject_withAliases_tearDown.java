package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.alias.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.alias.dom.AliasForDemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;

public class DemoObject_withAliases_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(AliasForDemoObject.class);
        deleteFrom(DemoObject.class);
    }

}
