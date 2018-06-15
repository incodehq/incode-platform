package org.incode.domainapp.extended.module.fixtures.shared.demowithurl.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoObjectWithUrl_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoObjectWithUrl\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
