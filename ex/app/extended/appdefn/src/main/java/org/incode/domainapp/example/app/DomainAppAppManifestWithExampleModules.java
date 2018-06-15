package org.incode.domainapp.example.app;

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

import org.incode.domainapp.extended.module.fixtures.ExampleDomSubmodule;
import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.example.alias.dom.AliasModule;
import org.incode.example.classification.dom.ClassificationModule;
import org.incode.example.commchannel.dom.CommChannelModule;
import org.incode.example.communications.dom.CommunicationsModuleDomModule;
import org.incode.example.country.dom.CountryModule;
import org.incode.example.docfragment.dom.DocFragmentModuleDomModule;
import org.incode.example.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.example.docrendering.stringinterpolator.dom.StringInterpolatorDocRenderingModule;
import org.incode.example.docrendering.xdocreport.dom.XDocReportDocRenderingModule;
import org.incode.example.document.dom.DocumentModule;
import org.incode.example.note.dom.NoteModule;
import org.incode.example.tags.TagsModule;

import domainapp.appdefn.DomainAppAppManifest;

public class DomainAppAppManifestWithExampleModules extends AppManifestAbstract {

    public static final Builder BUILDER = DomainAppAppManifest.BUILDER
            .withAdditionalModules(
                    ExampleAppSubmodule.class,
                    ExampleDomSubmodule.class,
                    ExampleDomDemoDomSubmodule.class,

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
                    CommunicationsModuleDomModule.class,
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
