package org.incode.domainapp.example.dom.ext.togglz.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectRepository;

public class DemoObjectsFixture extends DiscoverableFixtureScript {

    public DemoObjectsFixture() {
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

    private DemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, demoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private DemoObjectRepository demoObjects;

}
