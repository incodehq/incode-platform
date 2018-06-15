package org.incode.domainapp.extended.module.fixtures.shared.demo.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoObject_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoObject\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
