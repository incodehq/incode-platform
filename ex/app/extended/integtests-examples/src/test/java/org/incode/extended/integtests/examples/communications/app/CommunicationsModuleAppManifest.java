package org.incode.extended.integtests.examples.communications.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;

import org.incode.example.communications.dom.CommunicationsModule;
import org.incode.example.country.dom.CountryModule;
import org.incode.example.document.dom.DocumentModule;
import org.incode.extended.integtests.examples.communications.app.services.DemoAppApplicationModuleServicesSubmodule;
import org.incode.extended.integtests.examples.communications.demo.CommunicationsModuleDemoDomSubmodule;

/**
 * Bootstrap the application.
 */
public class CommunicationsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(CommunicationsModule.class,
            DocumentModule.class,
            CountryModule.class,
            CommunicationsModuleDemoDomSubmodule.class,
            DemoAppApplicationModuleServicesSubmodule.class,
            PdfBoxModule.class,
            CommandModule.class,
            FreeMarkerModule.class,
            FakeDataModule.class
    );


    public CommunicationsModuleAppManifest() {
        super(BUILDER);
    }

}
