package org.incode.extended.integtests.examples.alias.demo.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.extended.integtests.examples.alias.demo.fixture.teardown.sub.DemoObject_tearDown;

public class DemoModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DemoObject_tearDown());
    }

}
