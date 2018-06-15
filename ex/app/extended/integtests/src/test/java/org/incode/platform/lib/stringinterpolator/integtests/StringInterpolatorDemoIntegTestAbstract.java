package org.incode.platform.lib.stringinterpolator.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.platform.lib.stringinterpolator.integtests.app.StringInterpolatorLibAppManifest;

public abstract class StringInterpolatorDemoIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                StringInterpolatorLibAppManifest.BUILDER
                        .withAdditionalModules(ExampleDomDemoDomSubmodule.class)
                        .withConfigurationProperty("isis.website", "http://isis.apache.org")
                        .build()
        );
    }

}
