package org.isisaddons.metamodel.paraname8.fixture.scripts;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class Paraname8DemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"Paraname8DemoObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
