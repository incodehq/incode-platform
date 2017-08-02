package org.isisaddons.module.security.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.security.app.SecuritySpiAppManifest;

public abstract class SecurityModuleAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new SecuritySpiAppManifest());
    }

}
