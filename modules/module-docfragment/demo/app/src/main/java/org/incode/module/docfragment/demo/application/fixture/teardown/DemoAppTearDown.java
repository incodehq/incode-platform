package org.incode.module.docfragment.demo.application.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.module.docfragment.demo.module.fixture.teardown.DemoModuleTearDown;
import org.incode.module.docfragment.fixture.teardown.DocFragmentModuleTearDown;

public class DemoAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DocFragmentModuleTearDown());
        executionContext.executeChild(this, new DemoModuleTearDown());
    }

}
