package org.incode.extended.integtests.examples.docfragment.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.isisaddons.module.freemarker.dom.FreeMarkerModule;

import org.incode.extended.integtests.examples.docfragment.app.fixture.DemoAppApplicationModuleFixtureSubmodule;
import org.incode.example.docfragment.dom.DocFragmentModuleDomModule;

import org.incode.domainapp.extended.module.fixtures.per_cpt.examples.docfragment.ExampleDomModuleDocFragmentModule;

/**
 * Bootstrap the application.
 */
public class DocFragmentAppAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            DocFragmentModuleDomModule.class,
            ExampleDomModuleDocFragmentModule.class,
            DemoAppApplicationModuleFixtureSubmodule.class,

            FreeMarkerModule.class  // required by DocFragmentModule, do not yet support transitivity
    );

    public DocFragmentAppAppManifest() {
        super(BUILDER);
    }


}
