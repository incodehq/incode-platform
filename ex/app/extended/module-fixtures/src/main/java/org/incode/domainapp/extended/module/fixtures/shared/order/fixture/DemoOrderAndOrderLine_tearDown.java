package org.incode.domainapp.extended.module.fixtures.shared.order.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoOrderAndOrderLine_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoOrderLine\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoOrder\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
