package org.incode.platform.dom.communications.integtests.app.fixtures.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.communications.fixture.DemoModuleTearDown;

public class DemoAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DemoModuleTearDown());
    }

}
