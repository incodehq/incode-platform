package org.incode.module.alias.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.module.alias.dom.AliasModule;

import domainapp.modules.exampledom.module.alias.ExampleDomModuleAliasModule;

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
