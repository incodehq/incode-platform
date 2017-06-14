package domainapp.application.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import domainapp.modules.simple.fixture.teardown.FlywayDemoModuleTearDown;

public class DomainAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new FlywayDemoModuleTearDown());
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
