package org.isisaddons.module.servletapi.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

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
