package org.incode.example.alias.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.module.fixtures.per_cpt.examples.alias.ExampleDomModuleAliasModule;
import org.incode.example.alias.dom.AliasModule;


/**
 * Bootstrap the application.
 */
public class AliasModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            AliasModule.class, // dom module
            ExampleDomModuleAliasModule.class,
            AliasAppModule.class
    );

    public AliasModuleAppManifest() {
        super(BUILDER);
    }

}
