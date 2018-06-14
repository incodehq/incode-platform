package org.incode.example.document.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.stringinterpolator.StringInterpolatorModule;
import org.isisaddons.module.xdocreport.dom.XDocReportModule;

import org.incode.example.docrendering.freemarker.dom.FreemarkerDocRenderingModule;
import org.incode.example.docrendering.stringinterpolator.dom.StringInterpolatorDocRenderingModule;
import org.incode.example.docrendering.xdocreport.dom.XDocReportDocRenderingModule;
import org.incode.example.document.dom.DocumentModule;

import org.incode.domainapp.example.dom.dom.document.ExampleDomModuleDocumentModule;

/**
 * Bootstrap the application.
 */
public class DocumentModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            DocumentModule.class, // dom module
            ExampleDomModuleDocumentModule.class,
            DocumentAppModule.class,

            CommandModule.class,
            FakeDataModule.class,

            FreemarkerDocRenderingModule.class,
            FreeMarkerModule.class,

            StringInterpolatorDocRenderingModule.class,
            StringInterpolatorModule.class,

            XDocReportDocRenderingModule.class,
            XDocReportModule.class
    );

    public DocumentModuleAppManifest() {
        super(BUILDER);
    }

}
