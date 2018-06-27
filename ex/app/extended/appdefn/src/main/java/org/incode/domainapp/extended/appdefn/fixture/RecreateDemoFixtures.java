package org.incode.domainapp.extended.appdefn.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.metamodel.MetaModelService5;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.docx.fixture.order.DemoOrderAndOrderLine_create4_hardcodedData;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.excel.fixture.DemoToDoItem_create_usingExcelFixture;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.fixture.DemoObjectWithAll_create3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.fixture.Case_FixedAsset_Party_createAll;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.servletapi.fixture.ServletApiDemoObject_create3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.stringinterpolator.fixture.DemoReminder_create4;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture.sub.SomeAuditedObject_create3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture.sub.SomeNotAuditedObject_create3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.fixture.SomeCommandAnnotatedObject_create3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.fixture.PublishMqDemoObject_create3;
import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture.SecurityModuleAppSetUp;
import org.incode.domainapp.extended.module.fixtures.per_cpt.wkt.pdfjs.fixture.DemoObjectWithBlob_createUpTo5_fakeData;
import org.incode.domainapp.extended.module.fixtures.shared.demo.fixture.DemoObject_createUpTo10_hardcodedData;
import org.incode.example.classification.demo.usage.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3;
import org.incode.example.communications.demo.usage.fixture.demoobjwithnote.CommsCustomer_and_CommsInvoice_create3;
import org.incode.example.communications.demo.usage.fixture.doctypes.DocumentType_and_DocumentTemplates_createSome;
import org.incode.example.country.fixture.CountriesRefData;
import org.incode.example.country.fixture.StatesRefData;
import org.incode.example.docfragment.demo.usage.fixture.DocFragCustomer_and_DocFragInvoice_and_fragments_create;
import org.incode.example.document.demo.usage.fixture.seed.RenderingStrategy_create6;
import org.incode.example.tags.demo.shared.fixture.TaggableObject_withTags_create3;

public class RecreateDemoFixtures extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, metaModelService5.getAppManifest2().getTeardownFixture());

        ec.executeChild(this, new SecurityModuleAppSetUp());

        ec.executeChild(this, new RenderingStrategy_create6());
        ec.executeChild(this, new DocumentType_and_DocumentTemplates_createSome());
        queryResultsCache.resetForNextTransaction();

        ec.executeChild(this, new CountriesRefData());
        ec.executeChild(this, new StatesRefData());

        ec.executeChild(this, new SomeAuditedObject_create3());
        ec.executeChild(this, new SomeNotAuditedObject_create3());
        ec.executeChild(this, new SomeCommandAnnotatedObject_create3());
        ec.executeChild(this, new PublishMqDemoObject_create3());

        ec.executeChild(this, new TaggableObject_withTags_create3());
        ec.executeChild(this, new DemoOrderAndOrderLine_create4_hardcodedData());
        ec.executeChild(this, new Case_FixedAsset_Party_createAll());
        ec.executeChild(this, new ServletApiDemoObject_create3());

        ec.executeChild(this, new DemoObject_createUpTo10_hardcodedData());
        ec.executeChild(this, new DemoObjectWithAll_create3());
        ec.executeChild(this, new DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3());
        ec.executeChild(this, new DemoObjectWithBlob_createUpTo5_fakeData());
        ec.executeChild(this, new CommsCustomer_and_CommsInvoice_create3());

        ec.executeChild(this, new DocFragCustomer_and_DocFragInvoice_and_fragments_create());
        ec.executeChild(this, new DemoReminder_create4());
        ec.executeChild(this, new DemoToDoItem_create_usingExcelFixture("sven"));

    }

    @Inject
    MetaModelService5 metaModelService5;

    @Inject
    QueryResultsCache queryResultsCache;

}
