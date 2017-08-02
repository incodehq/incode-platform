package org.incode.platform.ext.flywaydb.integtests.app.fixtures.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.ext.flywaydb.fixture.teardown.FlywayDemoModuleTearDown;

public class DomainAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new FlywayDemoModuleTearDown());
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
