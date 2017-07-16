package domainapp.modules.exampledom.spi.command.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import domainapp.modules.exampledom.spi.command.dom.demo.SomeCommandAnnotatedObject;
import domainapp.modules.exampledom.spi.command.dom.demo.SomeCommandAnnotatedObjects;

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
