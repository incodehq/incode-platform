package org.incode.extended.integtests.examples.alias.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.example.alias.dom.AliasModule;
import org.incode.extended.integtests.examples.alias.dom.alias.AliasModuleIntegrationSubmodule;

/**
 * Bootstrap the application.
 */
public class AliasModuleAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            AliasModule.class, // dom module
            AliasModuleIntegrationSubmodule.class,
            AliasAppModule.class
    );

    public AliasModuleAppManifest() {
        super(BUILDER);
    }

}
