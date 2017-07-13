package org.isisaddons.module.servletapi.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class ServletApiDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"ServletApiDemoObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
