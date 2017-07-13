package org.isisaddons.module.togglz.fixture.scripts.scenarios;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.module.togglz.fixture.dom.module.demo.TogglzDemoObject;
import org.isisaddons.module.togglz.fixture.dom.module.demo.TogglzDemoObjects;
import org.isisaddons.module.togglz.fixture.scripts.teardown.TogglzDemoObjectsTearDownFixture;

public class TogglzDemoObjectsFixture extends DiscoverableFixtureScript {

    public TogglzDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new TogglzDemoObjectsTearDownFixture());

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private TogglzDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, togglzDemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private TogglzDemoObjects togglzDemoObjects;

}
