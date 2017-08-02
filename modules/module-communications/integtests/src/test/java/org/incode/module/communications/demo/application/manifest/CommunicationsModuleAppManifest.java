package org.incode.module.communications.demo.application.manifest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.AppManifestAbstract;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;

import org.incode.module.communications.demo.application.fixture.DemoAppApplicationModuleFixtureSubmodule;
import org.incode.module.communications.demo.application.services.DemoAppApplicationModuleServicesSubmodule;
import domainapp.modules.exampledom.module.communications.ExampleDomModuleCommunicationsModule;
import org.incode.module.communications.dom.CommunicationsModuleDomModule;
import org.incode.module.country.dom.CountryModule;
import org.incode.module.document.dom.DocumentModule;

/**
 * Bootstrap the application.
 */
public class CommunicationsModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.withModules(CommunicationsModuleDomModule.class,
            DocumentModule.class,
            CountryModule.class,
            ExampleDomModuleCommunicationsModule.class,
            DemoAppApplicationModuleFixtureSubmodule.class,
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
