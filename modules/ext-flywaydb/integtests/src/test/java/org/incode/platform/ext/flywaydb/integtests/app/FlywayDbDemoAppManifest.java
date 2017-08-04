package org.incode.platform.ext.flywaydb.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.example.dom.ExampleDomSubmodule;

/**
 * Bootstrap the application.
 */
public class FlywayDbDemoAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            ExampleDomSubmodule.class
    );

    public FlywayDbDemoAppManifest() {
        super(BUILDER);
    }

}
