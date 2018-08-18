package org.isisaddons.module.servletapi.fixture.scripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

public class ServletApiDemoObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(ServletApiDemoObject.class);
    }

}
