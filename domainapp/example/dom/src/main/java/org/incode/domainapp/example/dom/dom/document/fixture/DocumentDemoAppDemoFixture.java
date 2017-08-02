package org.incode.domainapp.example.dom.dom.document.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.dom.document.fixture.data.DemoObjectsFixture;
import org.incode.domainapp.example.dom.dom.document.fixture.data.OtherObjectsFixture;

import org.incode.domainapp.example.dom.dom.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

public class DocumentDemoAppDemoFixture extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocumentDemoAppTearDownFixture());

        ec.executeChild(this, new DocumentTypeAndTemplatesApplicableForDemoObjectFixture());

        ec.executeChild(this, new DemoObjectsFixture());
        ec.executeChild(this, new OtherObjectsFixture());
    }


}
