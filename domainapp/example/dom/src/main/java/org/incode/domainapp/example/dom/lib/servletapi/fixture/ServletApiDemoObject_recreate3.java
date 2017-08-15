package org.incode.domainapp.example.dom.lib.servletapi.fixture;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.incode.domainapp.example.dom.lib.servletapi.dom.demo.ServletApiDemoObject;
import org.incode.domainapp.example.dom.lib.servletapi.dom.demo.ServletApiDemoObjects;

public class ServletApiDemoObject_recreate3 extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new ServletApiDemoObject_tearDown());

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
    ServletApiDemoObjects servletApiDemoObjects;

}
