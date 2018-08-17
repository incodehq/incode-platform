package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.dom.SomeCommandAnnotatedObject;

public class SomeCommandAnnotatedObjects_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(SomeCommandAnnotatedObject.class);
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
