package org.incode.module.document.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.module.document.fixture.scripts.data.DemoObjectsFixture;
import org.incode.module.document.fixture.scripts.data.OtherObjectsFixture;
import org.incode.module.document.fixture.scripts.teardown.DocumentDemoAppTearDownFixture;
import org.incode.module.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

public class DocumentDemoAppDemoFixture extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocumentDemoAppTearDownFixture());

        ec.executeChild(this, new DocumentTypeAndTemplatesApplicableForDemoObjectFixture());

        ec.executeChild(this, new DemoObjectsFixture());
        ec.executeChild(this, new OtherObjectsFixture());
    }


}
