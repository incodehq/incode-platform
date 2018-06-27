package org.incode.example.alias.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.alias.demo.usage.dom.AliasForAliasedObject;
import org.incode.example.alias.demo.shared.dom.AliasedObject;

public class AliasedObject_withAliases_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(AliasForAliasedObject.class);
        deleteFrom(AliasedObject.class);
    }

}
