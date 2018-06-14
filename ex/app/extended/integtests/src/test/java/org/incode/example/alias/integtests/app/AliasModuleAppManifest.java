package org.incode.example.alias.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.alias.dom.AliasModule;

import org.incode.domainapp.example.dom.dom.alias.ExampleDomModuleAliasModule;

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
