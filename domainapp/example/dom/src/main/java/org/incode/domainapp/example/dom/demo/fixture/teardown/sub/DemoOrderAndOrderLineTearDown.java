package org.incode.domainapp.example.dom.demo.fixture.teardown.sub;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoOrderAndOrderLineTearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoOrderLine\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoOrder\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
