package org.incode.domainapp.example.dom.dom.document.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.setup.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.domainapp.example.dom.demo.fixture.setup.OtherObject_createUpTo5_fakeData;
import org.incode.domainapp.example.dom.dom.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

public class DemoObjectWithUrl_and_OtherObject_and_docrefdata_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocumentTypeAndTemplatesApplicableForDemoObjectFixture());

        ec.executeChild(this, new DemoObjectWithUrl_createUpTo5_fakeData());
        ec.executeChild(this, new OtherObject_createUpTo5_fakeData());
    }


}
