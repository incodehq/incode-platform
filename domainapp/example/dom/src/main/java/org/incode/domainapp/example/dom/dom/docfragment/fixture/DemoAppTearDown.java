package org.incode.domainapp.example.dom.dom.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;
import org.incode.domainapp.example.dom.dom.docfragment.fixture.teardown.DocFragmentModuleTearDown;

public class DemoAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DocFragmentModuleTearDown());
        executionContext.executeChild(this, new DemoModuleTearDown());
    }

}
