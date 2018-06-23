package org.incode.example.alias.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.alias.demo.usage.dom.AliasForDemoObject;
import org.incode.example.alias.demo.shared.dom.DemoObject;

public class DemoObject_withAliases_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(AliasForDemoObject.class);
        deleteFrom(DemoObject.class);
    }

}
