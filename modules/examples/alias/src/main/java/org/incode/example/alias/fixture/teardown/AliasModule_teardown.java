package org.incode.example.alias.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.alias.dom.impl.Alias;

public class AliasModule_teardown extends TeardownFixtureAbstract2 {
    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(Alias.class);
    }
}
