package org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs.dom.DemoObjectWithBlob;

public class DemoObjectWithBlob_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithBlob.class);
    }

}
