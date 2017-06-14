package org.isisaddons.wicket.pdfjs.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.wicket.pdfjs.fixture.scripts.data.DemoObjectsFixture;
import org.isisaddons.wicket.pdfjs.fixture.scripts.teardown.PdfjsDemoAppTearDownFixture;


public class PdfjsDemoAppDemoFixture extends DiscoverableFixtureScript {

    public PdfjsDemoAppDemoFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new PdfjsDemoAppTearDownFixture());

        ec.executeChild(this, new DemoObjectsFixture());
    }


}
