package org.incode.platform.ext.flywaydb.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;

/**
 * Bootstrap the application.
 */
public class FlywayDbDemoAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            ExampleDemoSubmodule.class
    );

    public FlywayDbDemoAppManifest() {
        super(BUILDER);
    }

}
