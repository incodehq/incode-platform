package org.incode.domainapp.extended.module.fixtures.shared.demowithblob.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoObjectWithBlob_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoObjectWithBlob\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
