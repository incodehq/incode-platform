package org.incode.domainapp.example.dom.ext.togglz.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.sub.DemoObjectTearDown;

public class TogglzDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        executionContext.executeChild(this, new DemoObjectTearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
