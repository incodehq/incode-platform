package org.incode.domainapp.module.fixtures.per_cpt.examples.document.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.module.fixtures.shared.demowithurl.fixture.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.domainapp.module.fixtures.shared.other.fixture.OtherObject_createUpTo5_fakeData;
import org.incode.domainapp.module.fixtures.per_cpt.examples.document.fixture.seed.DocumentTypeAndTemplatesApplicableForDemoObjectFixture;

public class DemoObjectWithUrl_and_OtherObject_and_docrefdata_create extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new DocumentTypeAndTemplatesApplicableForDemoObjectFixture());

        ec.executeChild(this, new DemoObjectWithUrl_createUpTo5_fakeData());
        ec.executeChild(this, new OtherObject_createUpTo5_fakeData());
    }


}
