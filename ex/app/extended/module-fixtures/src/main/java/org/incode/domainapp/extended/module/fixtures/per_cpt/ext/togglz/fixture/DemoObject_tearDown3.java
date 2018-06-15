package org.incode.domainapp.extended.module.fixtures.per_cpt.ext.togglz.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.extended.module.fixtures.shared.demo.fixture.DemoObject_tearDown;

public class DemoObject_tearDown3 extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        executionContext.executeChild(this, new DemoObject_tearDown());
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
