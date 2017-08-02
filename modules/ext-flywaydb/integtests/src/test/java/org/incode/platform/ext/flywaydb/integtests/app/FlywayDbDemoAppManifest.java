package org.incode.platform.ext.flywaydb.integtests.app;

import org.apache.isis.applib.AppManifestAbstract;

import org.incode.platform.ext.flywaydb.integtests.app.fixtures.FlywayDemoFixtureSubmodule;

import domainapp.modules.exampledom.ext.flywaydb.ExampleDomExtFlywayDbModule;

/**
 * Bootstrap the application.
 */
public class FlywayDbDemoAppManifest extends AppManifestAbstract {

    public static final Builder BUILDER = Builder.forModules(
            ExampleDomExtFlywayDbModule.class,
            FlywayDemoFixtureSubmodule.class

    );

    public FlywayDbDemoAppManifest() {
        super(BUILDER);
    }

}
