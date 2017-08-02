package domainapp.modules.exampledom.module.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.docfragment.fixture.teardown.DemoModuleTearDown;
import domainapp.modules.exampledom.module.docfragment.fixture.teardown.DocFragmentModuleTearDown;

public class DemoAppTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChild(this, new DocFragmentModuleTearDown());
        executionContext.executeChild(this, new DemoModuleTearDown());
    }

}
