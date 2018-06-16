package org.incode.domainapp.extended.integtests.lib.stringinterpolator;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.ExampleDomDemoDomSubmodule;
import org.incode.domainapp.extended.integtests.lib.stringinterpolator.app.StringInterpolatorLibAppManifest;

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
