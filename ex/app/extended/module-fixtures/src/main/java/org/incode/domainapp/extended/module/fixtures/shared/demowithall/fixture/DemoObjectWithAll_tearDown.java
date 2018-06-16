package org.incode.domainapp.extended.module.fixtures.shared.demowithall.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;

public class DemoObjectWithAll_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        deleteFrom(DemoObject.class);
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
