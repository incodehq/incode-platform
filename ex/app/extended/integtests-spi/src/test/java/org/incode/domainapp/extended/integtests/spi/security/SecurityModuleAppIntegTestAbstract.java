package org.incode.domainapp.extended.integtests.spi.security;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.domainapp.extended.integtests.spi.security.app.SecuritySpiAppManifest;

public abstract class SecurityModuleAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(SecuritySpiAppManifest.BUILDER
                .withAdditionalModules(FixturesModuleSharedSubmodule.class)
        );
    }

}
