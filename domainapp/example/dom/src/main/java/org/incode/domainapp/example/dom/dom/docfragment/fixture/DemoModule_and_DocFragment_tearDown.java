package org.incode.domainapp.example.dom.dom.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;
import org.incode.domainapp.example.dom.dom.docfragment.fixture.sub.DocFragment_tearDown;

public class DemoModule_and_DocFragment_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DocFragment_tearDown());
        executionContext.executeChild(this, new DemoModuleTearDown());
    }

}
