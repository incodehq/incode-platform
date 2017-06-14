package org.isisaddons.metamodel.paraname8.fixture.scripts;

import org.isisaddons.metamodel.paraname8.fixture.dom.Paraname8DemoObject;
import org.isisaddons.metamodel.paraname8.fixture.dom.Paraname8DemoObjects;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

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
