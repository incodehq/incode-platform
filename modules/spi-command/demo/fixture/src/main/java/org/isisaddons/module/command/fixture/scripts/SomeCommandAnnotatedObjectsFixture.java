package org.isisaddons.module.command.fixture.scripts;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.wrapper.WrapperFactory;

import org.isisaddons.module.command.fixture.dom.SomeCommandAnnotatedObject;
import org.isisaddons.module.command.fixture.dom.SomeCommandAnnotatedObjects;

public class SomeCommandAnnotatedObjectsFixture extends DiscoverableFixtureScript {

    public SomeCommandAnnotatedObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executeChild(new SomeCommandAnnotatedObjectsTearDownFixture(), executionContext);

        // create
        final SomeCommandAnnotatedObject foo = create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private SomeCommandAnnotatedObject create(final String name, ExecutionContext executionContext) {
        return executionContext.add(this, someCommandAnnotatedObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SomeCommandAnnotatedObjects someCommandAnnotatedObjects;

}
