package org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

public class DemoObjectWithBlob_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObjectWithBlob.class);
    }

}
