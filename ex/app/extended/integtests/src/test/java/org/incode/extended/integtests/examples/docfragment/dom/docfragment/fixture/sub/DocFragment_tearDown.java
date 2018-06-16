package org.incode.extended.integtests.examples.docfragment.dom.docfragment.fixture.sub;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.docfragment.dom.impl.DocFragment;

public class DocFragment_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(DocFragment.class);
    }


}
