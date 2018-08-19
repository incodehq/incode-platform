package org.isisaddons.module.command.fixture.demoapp.demomodule.fixturescripts.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.isisaddons.module.command.fixture.demoapp.demomodule.dom.SomeCommandAnnotatedObject;

public class SomeCommandAnnotatedObjects_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(SomeCommandAnnotatedObject.class);
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
