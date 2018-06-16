package org.incode.domainapp.extended.integtests.ext.flywaydb;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.integtests.ext.flywaydb.app.FlywayDbDemoAppManifest;

public abstract class FlywayDbModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(FlywayDbDemoAppManifest.BUILDER.build());
    }

}
