package org.incode.domainapp.example.dom.spi.command.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class SomeCommandAnnotatedObjects_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleSpiCommand\".\"SomeCommandAnnotatedObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
