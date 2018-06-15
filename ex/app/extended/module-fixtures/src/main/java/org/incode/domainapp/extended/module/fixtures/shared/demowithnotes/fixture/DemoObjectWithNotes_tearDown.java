package org.incode.domainapp.extended.module.fixtures.shared.demowithnotes.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoObjectWithNotes_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDemo\".\"DemoObjectWithNotes\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
