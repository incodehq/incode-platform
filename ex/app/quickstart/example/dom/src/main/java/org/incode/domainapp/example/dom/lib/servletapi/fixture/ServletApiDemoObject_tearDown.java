package org.incode.domainapp.example.dom.lib.servletapi.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class ServletApiDemoObject_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleLibServletApi\".\"ServletApiDemoObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
