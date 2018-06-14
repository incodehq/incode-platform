package org.incode.platform.ext.flywaydb.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.platform.ext.flywaydb.integtests.app.FlywayDbDemoAppManifest;

public abstract class FlywayDbModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(FlywayDbDemoAppManifest.BUILDER.build());
    }

}
