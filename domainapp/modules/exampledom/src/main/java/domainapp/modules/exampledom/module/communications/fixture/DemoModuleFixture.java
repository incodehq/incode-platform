package domainapp.modules.exampledom.module.communications.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import domainapp.modules.exampledom.module.communications.fixture.data.doctypes.DocumentTypesAndTemplatesFixture;
import domainapp.modules.exampledom.module.communications.fixture.data.doctypes.RenderingStrategiesFixture;
import domainapp.modules.exampledom.module.communications.fixture.data.demo.DemoCustomersFixture;

import org.incode.module.country.fixture.CountriesRefData;

public class DemoModuleFixture extends DiscoverableFixtureScript {

    public DemoModuleFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // teardown
        executionContext.executeChild(this, new DemoModuleTearDown());

        // prereqs
    	executionContext.executeChild(this, new CountriesRefData());
        executionContext.executeChild(this, new RenderingStrategiesFixture());
        executionContext.executeChild(this, new DocumentTypesAndTemplatesFixture());
    	queryResultsCache.resetForNextTransaction();

    	executionContext.executeChild(this, new DemoCustomersFixture());
    }

    @Inject
    QueryResultsCache queryResultsCache;

}
