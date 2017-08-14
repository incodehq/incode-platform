package org.incode.platform.spi.security.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;
import org.incode.platform.spi.security.integtests.app.SecuritySpiAppManifest;

public abstract class SecurityModuleAppIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(SecuritySpiAppManifest.BUILDER
                .withAdditionalModules(ExampleDemoSubmodule.class)
        );
    }

}
