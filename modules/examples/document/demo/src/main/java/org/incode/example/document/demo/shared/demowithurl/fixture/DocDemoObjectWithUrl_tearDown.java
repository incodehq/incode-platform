package org.incode.example.document.demo.shared.demowithurl.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.document.demo.shared.demowithurl.dom.DocDemoObjectWithUrl;

public class DocDemoObjectWithUrl_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DocDemoObjectWithUrl.class);
    }


}
