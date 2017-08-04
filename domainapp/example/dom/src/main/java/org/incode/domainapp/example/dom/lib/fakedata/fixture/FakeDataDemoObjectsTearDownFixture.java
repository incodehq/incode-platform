package org.incode.domainapp.example.dom.lib.fakedata.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectWithAllTearDown;

public class FakeDataDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        executionContext.executeChild(this, new DemoObjectWithAllTearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
