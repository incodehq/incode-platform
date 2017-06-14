package org.isisaddons.module.togglz.fixture.scripts.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class TogglzDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"togglz\".\"TogglzDemoObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
