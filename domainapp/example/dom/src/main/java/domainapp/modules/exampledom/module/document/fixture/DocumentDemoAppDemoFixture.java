package domainapp.modules.exampledom.module.document.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import domainapp.modules.exampledom.module.document.fixture.data.DemoObjectsFixture;
import domainapp.modules.exampledom.module.document.fixture.data.OtherObjectsFixture;

import domainapp.modules.exampledom.module.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

public class DocumentDemoAppDemoFixture extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocumentDemoAppTearDownFixture());

        ec.executeChild(this, new DocumentTypeAndTemplatesApplicableForDemoObjectFixture());

        ec.executeChild(this, new DemoObjectsFixture());
        ec.executeChild(this, new OtherObjectsFixture());
    }


}
