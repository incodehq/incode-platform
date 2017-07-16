package org.incode.module.communications.demo.application.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.communications.fixture.DemoModuleTearDown;

public class DemoAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DemoModuleTearDown());
    }

}
