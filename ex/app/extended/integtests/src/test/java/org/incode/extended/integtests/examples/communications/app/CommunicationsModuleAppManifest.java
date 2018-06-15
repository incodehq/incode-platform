package org.incode.extended.integtests.examples.communications.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.communications.ExampleDomModuleCommunicationsModule;
import org.incode.example.communications.dom.CommunicationsModuleDomModule;
import org.incode.extended.integtests.examples.communications.app.services.DemoAppApplicationModuleServicesSubmodule;
import org.incode.example.country.dom.CountryModule;
import org.incode.example.document.dom.DocumentModule;

/**
 * Bootstrap the application.
 */
public class CommunicationsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(CommunicationsModuleDomModule.class,
            DocumentModule.class,
            CountryModule.class,
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
