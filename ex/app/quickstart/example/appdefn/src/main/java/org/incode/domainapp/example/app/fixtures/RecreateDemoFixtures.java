package org.incode.domainapp.example.app.fixtures;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.incode.domainapp.example.dom.demo.fixture.reminders.DemoReminder_create4;
import org.incode.domainapp.example.dom.demo.fixture.setup.DemoObjectWithBlob_createUpTo5_fakeData;
import org.incode.domainapp.example.dom.demo.fixture.setup.DemoObjectWithUrl_createUpTo5_fakeData;
import org.incode.domainapp.example.dom.demo.fixture.setup.DemoObject_createUpTo10_hardcodedData;
import org.incode.domainapp.example.dom.demo.fixture.setup.DemoOrderAndOrderLine_recreate4_hardcodedData;
import org.incode.domainapp.example.dom.demo.fixture.setup.OtherObject_createUpTo5_fakeData;
import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;
import org.incode.domainapp.example.dom.demo.fixture.todoitems.DemoToDoItem_create_usingExcelFixture;
import org.incode.domainapp.example.dom.dom.classification.fixture.ClassificationModule_tearDown;
import org.incode.domainapp.example.dom.dom.classification.fixture.DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.CommChannelModule_tearDown;
import org.incode.domainapp.example.dom.dom.communications.fixture.data.democust2.DemoObjectWithNote_and_DemoInvoice_create3;
import org.incode.domainapp.example.dom.dom.communications.fixture.data.doctypes.DocumentType_and_DocumentTemplates_createSome;
import org.incode.domainapp.example.dom.dom.docfragment.fixture.DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create;
import org.incode.domainapp.example.dom.dom.docfragment.fixture.sub.DocFragment_tearDown;
import org.incode.domainapp.example.dom.dom.document.fixture.seed.RenderingStrategy_create6;
import org.incode.domainapp.example.dom.dom.tags.fixture.DemoTaggableObject_withTags_create3;
import org.incode.domainapp.example.dom.lib.fakedata.fixture.DemoObjectWithAll_recreate3;
import org.incode.domainapp.example.dom.lib.poly.fixture.Case_FixedAsset_Party_andLinks_tearDown;
import org.incode.domainapp.example.dom.lib.poly.fixture.Case_FixedAsset_Party_recreateAll;
import org.incode.domainapp.example.dom.lib.servletapi.fixture.ServletApiDemoObject_create3;
import org.incode.domainapp.example.dom.lib.servletapi.fixture.ServletApiDemoObject_tearDown;
import org.incode.domainapp.example.dom.spi.audit.fixture.sub.SomeAuditedObject_and_SomeNonAuditedObject_and_AuditEntry_tearDown;
import org.incode.domainapp.example.dom.spi.audit.fixture.sub.SomeAuditedObject_create3;
import org.incode.domainapp.example.dom.spi.audit.fixture.sub.SomeNotAuditedObject_create3;
import org.incode.domainapp.example.dom.spi.command.fixture.SomeCommandAnnotatedObject_create3;
import org.incode.domainapp.example.dom.spi.command.fixture.teardown.SomeCommandAnnotatedObjects_tearDown;
import org.incode.domainapp.example.dom.spi.publishmq.fixture.PublishMqDemoObject_create3;
import org.incode.domainapp.example.dom.spi.publishmq.fixture.teardown.PublishMqDemoObject_tearDown;
import org.incode.domainapp.example.dom.spi.security.fixture.SecurityModuleAppSetUp;
import org.incode.module.communications.fixture.teardown.CommunicationModule_tearDown;
import org.incode.module.country.fixture.CountriesRefData;
import org.incode.module.country.fixture.StatesRefData;
import org.incode.module.country.fixture.teardown.CountryModule_tearDown;
import org.incode.module.document.fixture.teardown.DocumentModule_tearDown;

public class RecreateDemoFixtures extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, new SecurityModuleAppSetUp());

        ec.executeChild(this, new ServletApiDemoObject_tearDown());
        ec.executeChild(this, new PublishMqDemoObject_tearDown());

        ec.executeChild(this, new ClassificationModule_tearDown());
        ec.executeChild(this, new CommChannelModule_tearDown());
        ec.executeChild(this, new CommunicationModule_tearDown());
        ec.executeChild(this, new DocumentModule_tearDown());
        ec.executeChild(this, new DocFragment_tearDown());
        ec.executeChild(this, new Case_FixedAsset_Party_andLinks_tearDown());
        ec.executeChild(this, new CountryModule_tearDown());
        ec.executeChild(this, new ServletApiDemoObject_tearDown());

        ec.executeChild(this, new SomeAuditedObject_and_SomeNonAuditedObject_and_AuditEntry_tearDown());
        ec.executeChild(this, new SomeCommandAnnotatedObjects_tearDown());
        ec.executeChild(this, new PublishMqDemoObject_tearDown());

        ec.executeChild(this, new DemoModuleTearDown());



        ec.executeChild(this, new RenderingStrategy_create6());
        ec.executeChild(this, new DocumentType_and_DocumentTemplates_createSome());
        queryResultsCache.resetForNextTransaction();

        ec.executeChild(this, new CountriesRefData());
        ec.executeChild(this, new StatesRefData());

        ec.executeChild(this, new SomeAuditedObject_create3());
        ec.executeChild(this, new SomeNotAuditedObject_create3());
        ec.executeChild(this, new SomeCommandAnnotatedObject_create3());
        ec.executeChild(this, new PublishMqDemoObject_create3());

        ec.executeChild(this, new DemoTaggableObject_withTags_create3());
        ec.executeChild(this, new DemoOrderAndOrderLine_recreate4_hardcodedData());
        ec.executeChild(this, new Case_FixedAsset_Party_recreateAll());
        ec.executeChild(this, new ServletApiDemoObject_create3());

        ec.executeChild(this, new DemoObject_createUpTo10_hardcodedData());
        ec.executeChild(this, new DemoObjectWithAll_recreate3());
        ec.executeChild(this, new DemoObjectWithAtPath_and_OtherObjectWithAtPath_create3());
        ec.executeChild(this, new DemoObjectWithBlob_createUpTo5_fakeData());
        ec.executeChild(this, new DemoObjectWithNote_and_DemoInvoice_create3());
        ec.executeChild(this, new DemoObjectWithUrl_createUpTo5_fakeData());

        ec.executeChild(this, new OtherObject_createUpTo5_fakeData());

        ec.executeChild(this, new DemoCustomer_and_DemoInvoiceWithAtPath_and_fragments_create());
        ec.executeChild(this, new DemoReminder_create4());
        ec.executeChild(this, new DemoToDoItem_create_usingExcelFixture("sven"));

    }

    @Inject
    QueryResultsCache queryResultsCache;

}
