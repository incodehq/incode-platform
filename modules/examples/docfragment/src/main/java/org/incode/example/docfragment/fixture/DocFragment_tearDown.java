package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.docfragment.dom.impl.DocFragment;

public class DocFragment_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(FixtureScript.ExecutionContext executionContext) {
        deleteFrom(DocFragment.class);
    }


}
