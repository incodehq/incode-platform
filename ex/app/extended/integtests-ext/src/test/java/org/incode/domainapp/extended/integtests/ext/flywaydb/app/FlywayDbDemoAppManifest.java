package org.incode.domainapp.extended.integtests.ext.flywaydb.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;

/**
 * Bootstrap the application.
 */
public class FlywayDbDemoAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            FixturesModuleSharedSubmodule.class
    );

    public FlywayDbDemoAppManifest() {
        super(BUILDER);
    }

}
