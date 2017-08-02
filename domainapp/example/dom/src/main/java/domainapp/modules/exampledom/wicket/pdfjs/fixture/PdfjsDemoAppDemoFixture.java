package domainapp.modules.exampledom.wicket.pdfjs.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import domainapp.modules.exampledom.wicket.pdfjs.fixture.data.DemoObjectsFixture;

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
