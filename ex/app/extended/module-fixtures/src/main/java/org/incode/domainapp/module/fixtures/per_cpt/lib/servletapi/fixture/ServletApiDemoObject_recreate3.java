package org.incode.domainapp.module.fixtures.per_cpt.lib.servletapi.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class ServletApiDemoObject_recreate3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        executionContext.executeChild(this, new ServletApiDemoObject_tearDown());
        executionContext.executeChild(this, new ServletApiDemoObject_create3());
    }


}
