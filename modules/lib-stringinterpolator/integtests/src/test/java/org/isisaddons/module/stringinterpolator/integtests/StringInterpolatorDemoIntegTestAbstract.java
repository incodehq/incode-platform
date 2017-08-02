package org.isisaddons.module.stringinterpolator.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.stringinterpolator.app.StringInterpolatorLibAppManifest;

public abstract class StringInterpolatorDemoIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(
                StringInterpolatorLibAppManifest.BUILDER
                        .withConfigurationProperty("isis.website", "http://isis.apache.org")
                        .build()
        );
    }

}
