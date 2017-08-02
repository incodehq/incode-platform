package org.incode.domainapp.example.dom.mml.paraname8.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.mml.paraname8.dom.Paraname8DemoObject;
import org.incode.domainapp.example.dom.mml.paraname8.dom.Paraname8DemoObjects;

public class Paraname8DemoObjectsFixture extends DiscoverableFixtureScript {

    public Paraname8DemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        execute(new Paraname8DemoObjectsTearDownFixture(), executionContext);

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private Paraname8DemoObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, paraname8DemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private Paraname8DemoObjects paraname8DemoObjects;

}
