package org.incode.domainapp.example.dom.dom.communications.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.domainapp.example.dom.dom.communications.fixture.data.doctypes.DocumentTypesAndTemplatesFixture;
import org.incode.domainapp.example.dom.dom.communications.fixture.data.doctypes.RenderingStrategiesFixture;
import org.incode.domainapp.example.dom.dom.communications.fixture.data.democust2.DemoCustomer2sFixture;

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

    	executionContext.executeChild(this, new DemoCustomer2sFixture());
    }

    @Inject
    QueryResultsCache queryResultsCache;

}
