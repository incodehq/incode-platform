package domainapp.modules.simple.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class SimpleModule_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"simple\".\"SimpleObject\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
