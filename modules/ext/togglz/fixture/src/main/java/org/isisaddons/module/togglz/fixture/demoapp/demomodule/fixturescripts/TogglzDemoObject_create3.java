package org.isisaddons.module.togglz.fixture.demoapp.demomodule.fixturescripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.TogglzDemoObject;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.TogglzDemoObjectRepository;

public class TogglzDemoObject_create3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private TogglzDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, demoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private TogglzDemoObjectRepository demoObjects;

}
