package org.incode.domainapp.extended.integtests.examples.document.dom.document.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.integtests.examples.document.demo.fixture.setup.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.domainapp.extended.integtests.examples.document.demo.fixture.setup.OtherObject_createUpTo5_fakeData;
import org.incode.domainapp.extended.integtests.examples.document.dom.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

public class DemoObjectWithUrl_and_OtherObject_and_docrefdata_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocumentTypeAndTemplatesApplicableForDemoObjectFixture());

        ec.executeChild(this, new DemoObjectWithUrl_createUpTo5_fakeData());
        ec.executeChild(this, new OtherObject_createUpTo5_fakeData());
    }


}
