package org.incode.module.docfragment.demo.application.manifest;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.freemarker.dom.FreeMarkerModule;

import org.incode.module.docfragment.demo.application.fixture.DemoAppApplicationModuleFixtureSubmodule;
import org.incode.module.docfragment.demo.application.services.DemoAppApplicationModuleServicesSubmodule;
import org.incode.module.docfragment.dom.DocFragmentModuleDomModule;

import domainapp.modules.exampledom.module.docfragment.ExampleDomModuleDocFragmentModule;

/**
 * Bootstrap the application.
 */
public class DocFragmentAppAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            DocFragmentModuleDomModule.class,
            ExampleDomModuleDocFragmentModule.class,
            DemoAppApplicationModuleFixtureSubmodule.class,
            DemoAppApplicationModuleServicesSubmodule.class,

            FreeMarkerModule.class  // required by DocFragmentModule, do not yet support transitivity
    );

    public DocFragmentAppAppManifest() {
        super(BUILDER);
    }


}
