package org.incode.example.document.demo.shared.demowithurl.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.incode.example.document.demo.shared.demowithurl.dom.DemoObjectWithUrl;

public class DemoObjectWithUrl_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithUrl.class);
    }


}
