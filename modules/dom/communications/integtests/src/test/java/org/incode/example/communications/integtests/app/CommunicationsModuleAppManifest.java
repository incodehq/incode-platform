package org.incode.example.communications.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;

import org.incode.domainapp.example.dom.dom.communications.ExampleDomModuleCommunicationsModule;
import org.incode.example.communications.dom.CommunicationsModuleDomModule;
import org.incode.example.communications.integtests.app.services.DemoAppApplicationModuleServicesSubmodule;
import org.incode.example.country.dom.CountryExample;
import org.incode.example.document.dom.DocumentModule;

/**
 * Bootstrap the application.
 */
public class CommunicationsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(CommunicationsModuleDomModule.class,
            DocumentModule.class,
            CountryExample.class,
            ExampleDomModuleCommunicationsModule.class,
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
