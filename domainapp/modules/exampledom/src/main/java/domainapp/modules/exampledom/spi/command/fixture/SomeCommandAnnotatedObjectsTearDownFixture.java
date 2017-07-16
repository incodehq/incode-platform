package domainapp.modules.exampledom.spi.command.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class SomeCommandAnnotatedObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"commanddemo\".\"SomeCommandAnnotatedObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
