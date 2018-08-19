package org.incode.platformapp.appdefn.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;
import org.apache.isis.applib.services.metamodel.MetaModelService5;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts.sub.SomeAuditedObject_create3;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.fixturescripts.sub.SomeNotAuditedObject_create3;
import org.isisaddons.module.command.fixture.demoapp.demomodule.fixturescripts.SomeCommandAnnotatedObject_create3;
import org.isisaddons.module.docx.fixture.fixturescripts.DocxDemoOrderAndOrderLine_create4_hardcodedData;
import org.isisaddons.module.excel.fixture.demoapp.demomodule.fixturescripts.DemoToDoItem_create_usingExcelFixture;
import org.isisaddons.module.fakedata.fixture.demoapp.demomodule.fixturescripts.FakeDataDemoObjectWithAll_create3;
import org.isisaddons.module.flywaydb.fixture.demomodule.fixturescripts.FlywayDbDemoObject_createUpTo10_hardcodedData;
import org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.PolyDemo_Case_FixedAsset_Party_createAll;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.fixturescripts.PublishMqDemoObject_create3;
import org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.SecurityModuleAppSetUp;
import org.isisaddons.module.servletapi.fixture.scripts.ServletApiDemoObject_create3;
import org.isisaddons.module.stringinterpolator.fixture.lib.stringinterpolator.scripts.OgnlDemoReminder_create4;
import org.isisaddons.wicket.pdfjs.fixture.demoapp.demomodule.fixturescripts.PdfJsDemoObjectWithBlob_createUpTo5_fakeData;

public class RecreateDemoFixtures extends DiscoverableFixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.executeChild(this, metaModelService5.getAppManifest2().getTeardownFixture());

        ec.executeChild(this, new SecurityModuleAppSetUp());

        queryResultsCache.resetForNextTransaction();

        ec.executeChild(this, new SomeAuditedObject_create3());
        ec.executeChild(this, new SomeNotAuditedObject_create3());
        ec.executeChild(this, new SomeCommandAnnotatedObject_create3());
        ec.executeChild(this, new PublishMqDemoObject_create3());

        ec.executeChild(this, new DocxDemoOrderAndOrderLine_create4_hardcodedData());
        ec.executeChild(this, new PolyDemo_Case_FixedAsset_Party_createAll());
        ec.executeChild(this, new ServletApiDemoObject_create3());

        ec.executeChild(this, new FlywayDbDemoObject_createUpTo10_hardcodedData());
        ec.executeChild(this, new FakeDataDemoObjectWithAll_create3());
        ec.executeChild(this, new PdfJsDemoObjectWithBlob_createUpTo5_fakeData());

        ec.executeChild(this, new OgnlDemoReminder_create4());
        ec.executeChild(this, new DemoToDoItem_create_usingExcelFixture("sven"));

    }

    @Inject
    MetaModelService5 metaModelService5;

    @Inject
    QueryResultsCache queryResultsCache;

}
