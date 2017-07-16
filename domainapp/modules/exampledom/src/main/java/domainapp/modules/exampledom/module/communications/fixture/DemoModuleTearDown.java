package domainapp.modules.exampledom.module.communications.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.module.communications.fixture.teardown.CommunicationModuleTearDown;
import org.incode.module.country.fixture.teardown.CountryModuleTearDown;
import org.incode.module.document.fixture.teardown.DocumentModuleTearDown;

public class DemoModuleTearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        isisJdoSupport.executeUpdate("delete from \"incodeCommunicationsDemo\".\"PaperclipForDemoInvoice\"");
        isisJdoSupport.executeUpdate("delete from \"incodeCommunicationsDemo\".\"DemoInvoice\"");
        isisJdoSupport.executeUpdate("delete from \"incodeCommunicationsDemo\".\"CommunicationChannelOwnerLinkForDemoCustomer\"");
        isisJdoSupport.executeUpdate("delete from \"incodeCommunicationsDemo\".\"DemoCustomer\"");

        executionContext.executeChild(this, new CommunicationModuleTearDown());
        executionContext.executeChild(this, new DocumentModuleTearDown());
        executionContext.executeChild(this, new CountryModuleTearDown());

    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
