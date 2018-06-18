package org.incode.domainapp.extended.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.excel.ExcelModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;
import org.isisaddons.module.poly.PolyModule;
import org.isisaddons.module.servletapi.ServletApiModule;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;
import org.isisaddons.module.xdocreport.dom.XDocReportModule;

import org.incode.domainapp.extended.appdefn.DomainAppAppManifest;
import org.incode.domainapp.extended.module.fixtures.FixturesModule;
import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.example.alias.AliasModule;
import org.incode.example.classification.ClassificationModule;
import org.incode.example.commchannel.CommChannelModule;
import org.incode.example.communications.CommunicationsModule;
import org.incode.example.country.CountryModule;
import org.incode.example.docfragment.dom.DocFragmentModuleDomModule;
import org.incode.example.docrendering.freemarker.FreemarkerDocRenderingModule;
import org.incode.example.docrendering.stringinterpolator.StringInterpolatorDocRenderingModule;
import org.incode.example.docrendering.xdocreport.XDocReportDocRenderingModule;
import org.incode.example.document.DocumentModule;
import org.incode.example.note.NoteModule;
import org.incode.example.tags.TagsModule;

public class DomainAppAppManifestWithExampleModules extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifest.BUILDER
            .withAdditionalModules(
                    ExampleAppSubmodule.class,
                    FixturesModule.class,
                    FixturesModuleSharedSubmodule.class,

                    // lib
                    FreemarkerDocRenderingModule.class,
                    StringInterpolatorDocRenderingModule.class,
                    XDocReportDocRenderingModule.class,
                    DocxModule.class,
                    ExcelModule.class,
                    FakeDataModule.class,
                    FreeMarkerModule.class,
                    PolyModule.class,
                    PdfBoxModule.class,
                    ServletApiModule.class,
                    StringInterpolatorModule.class,
                    XDocReportModule.class,

                    // subdomains (dom)
                    AliasModule.class,
                    ClassificationModule.class,
                    CommChannelModule.class,
                    CommunicationsModule.class,
                    CountryModule.class,
                    DocFragmentModuleDomModule.class,
                    DocumentModule.class,
                    NoteModule.class,
                    TagsModule.class

            );

    public DomainAppAppManifestWithExampleModules() {
        super(BUILDER);
    }

}
