package domainapp.modules.exampledom.ext.togglz.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import domainapp.modules.exampledom.ext.togglz.dom.demo.TogglzDemoObject;
import domainapp.modules.exampledom.ext.togglz.dom.demo.TogglzDemoObjects;

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
