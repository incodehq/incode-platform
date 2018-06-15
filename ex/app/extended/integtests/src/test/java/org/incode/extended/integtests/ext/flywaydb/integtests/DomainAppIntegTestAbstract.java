package org.incode.extended.integtests.ext.flywaydb.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.extended.integtests.ext.flywaydb.app.FlywayDbDemoAppManifest;

public abstract class DomainAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initSystem() {
        bootstrapUsing(new FlywayDbDemoAppManifest());
    }

}
