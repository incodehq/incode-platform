package org.isisaddons.module.servletapi.fixture.scripts;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import org.isisaddons.module.servletapi.fixture.demoapp.demomodule.dom.ServletApiDemoObject;

public class ServletApiDemoObject_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(ServletApiDemoObject.class);
    }

}
