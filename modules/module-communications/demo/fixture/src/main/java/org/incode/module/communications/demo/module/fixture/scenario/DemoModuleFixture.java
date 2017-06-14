package org.incode.module.communications.demo.module.fixture.scenario;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.module.communications.demo.module.fixture.data.doctypes.DocumentTypesAndTemplatesFixture;
import org.incode.module.communications.demo.module.fixture.data.doctypes.RenderingStrategiesFixture;
import org.incode.module.communications.demo.module.fixture.demodata.DemoCustomersFixture;
import org.incode.module.communications.demo.module.fixture.teardown.DemoModuleTearDown;
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
