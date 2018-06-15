package org.incode.domainapp.extended.module.fixtures.shared.other.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class OtherObject_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"OtherObject\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
