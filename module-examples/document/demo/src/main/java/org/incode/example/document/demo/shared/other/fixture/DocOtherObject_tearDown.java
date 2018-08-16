package org.incode.example.document.demo.shared.other.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.document.demo.shared.other.dom.DocOtherObject;

public class DocOtherObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DocOtherObject.class);
    }


}
