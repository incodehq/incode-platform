package domainapp.appdefn.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.fixture.teardown.SimpleModule_tearDown;

public class DomainAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new SimpleModule_tearDown());
    }

}
