package org.incode.domainapp.example.dom.dom.communications.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.domainapp.example.dom.dom.communications.fixture.data.doctypes.DocumentType_and_DocumentTemplates_createSome;
import org.incode.domainapp.example.dom.dom.communications.fixture.data.doctypes.RenderingStrategy_create1;
import org.incode.domainapp.example.dom.dom.communications.fixture.data.democust2.DemoObjectWithNote_and_DemoInvoice2_withComms_create3;

import org.incode.module.country.fixture.CountriesRefData;

public class DemoModuleFixture extends DiscoverableFixtureScript {

    public DemoModuleFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // teardown
        executionContext.executeChild(this, new DemoObjectWithNotes_and_DemoInvoice2_and_docs_and_comms_tearDown());

        // prereqs
    	executionContext.executeChild(this, new CountriesRefData());
        executionContext.executeChild(this, new RenderingStrategy_create1());
        executionContext.executeChild(this, new DocumentType_and_DocumentTemplates_createSome());
    	queryResultsCache.resetForNextTransaction();

    	executionContext.executeChild(this, new DemoObjectWithNote_and_DemoInvoice2_withComms_create3());
    }

    @Inject
    QueryResultsCache queryResultsCache;

}
