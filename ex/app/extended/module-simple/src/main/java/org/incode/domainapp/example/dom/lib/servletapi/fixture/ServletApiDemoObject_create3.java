package org.incode.domainapp.example.dom.lib.servletapi.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.servletapi.dom.demo.ServletApiDemoObject;
import org.incode.domainapp.example.dom.lib.servletapi.dom.demo.ServletApiDemoObjects;

public class ServletApiDemoObject_create3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        create("Foo", executionContext);
        create("Bar", executionContext);
        create("Baz", executionContext);
    }

    // //////////////////////////////////////

    private ServletApiDemoObject create(final String name, final ExecutionContext executionContext) {
        return executionContext.addResult(this, servletApiDemoObjects.createServletApiDemoObject(name));
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    ServletApiDemoObjects servletApiDemoObjects;

}
