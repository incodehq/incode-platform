package domainapp.modules.exampledom.lib.servletapi.fixture;

import domainapp.modules.exampledom.lib.servletapi.dom.demo.ServletApiDemoObject;
import domainapp.modules.exampledom.lib.servletapi.dom.demo.ServletApiDemoObjects;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

public class ServletApiDemoObjectsFixture extends DiscoverableFixtureScript {

    public ServletApiDemoObjectsFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new ServletApiDemoObjectsTearDownFixture());

        // create
        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private ServletApiDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, servletApiDemoObjects.create(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private ServletApiDemoObjects servletApiDemoObjects;

}
