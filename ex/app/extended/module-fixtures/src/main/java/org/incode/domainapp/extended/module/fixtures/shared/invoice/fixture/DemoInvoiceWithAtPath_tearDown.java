package org.incode.domainapp.extended.module.fixtures.shared.invoice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoInvoiceWithAtPath_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoInvoiceWithAtPath\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}