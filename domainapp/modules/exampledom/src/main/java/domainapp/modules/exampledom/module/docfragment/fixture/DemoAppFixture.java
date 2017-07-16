package domainapp.modules.exampledom.module.docfragment.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.docfragment.fixture.data.DemoCustomerData;
import domainapp.modules.exampledom.module.docfragment.fixture.data.DemoInvoiceData;
import domainapp.modules.exampledom.module.docfragment.fixture.data.DocFragmentData;

public class DemoAppFixture extends FixtureScript {

    public DemoAppFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        // execute
        ec.executeChild(this, new DemoAppTearDown());
        ec.executeChild(this, new DemoCustomerData.PersistScript());
        ec.executeChild(this, new DemoInvoiceData.PersistScript());
        ec.executeChild(this, new DocFragmentData.PersistScript());

    }
}
