package org.incode.example.document.demo.usage.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.example.document.demo.usage.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;
import org.incode.example.document.demo.shared.demowithurl.fixture.DocDemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.example.document.demo.shared.other.fixture.DocOtherObject_createUpTo5_fakeData;

public class DemoObjectWithUrl_and_OtherObject_and_docrefdata_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocumentTypeAndTemplatesApplicableForDemoObjectFixture());

        ec.executeChild(this, new DocDemoObjectWithUrl_createUpTo5_fakeData());
        ec.executeChild(this, new DocOtherObject_createUpTo5_fakeData());
    }


}
