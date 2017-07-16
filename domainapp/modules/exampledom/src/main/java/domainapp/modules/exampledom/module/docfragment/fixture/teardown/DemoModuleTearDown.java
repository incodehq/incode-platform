package domainapp.modules.exampledom.module.docfragment.fixture.teardown;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class DemoModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"incodeDocFragmentDemo\".\"DemoCustomer\"");
        isisJdoSupport.executeUpdate("delete from \"incodeDocFragmentDemo\".\"DemoInvoice\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
