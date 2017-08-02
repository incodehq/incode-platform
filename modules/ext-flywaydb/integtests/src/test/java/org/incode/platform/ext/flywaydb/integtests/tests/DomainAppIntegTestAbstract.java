package org.incode.platform.ext.flywaydb.integtests.tests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.platform.ext.flywaydb.integtests.app.FlywayDbDemoAppManifest;

public abstract class DomainAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(new FlywayDbDemoAppManifest());
    }

}
