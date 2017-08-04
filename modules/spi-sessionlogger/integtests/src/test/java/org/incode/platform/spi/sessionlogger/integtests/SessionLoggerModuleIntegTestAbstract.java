package org.incode.platform.spi.sessionlogger.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.example.dom.demo.ExampleDemoSubmodule;
import org.incode.platform.spi.sessionlogger.app.SessionLoggerAppManifest;

public abstract class SessionLoggerModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(SessionLoggerAppManifest.BUILDER
                .withAdditionalModules(ExampleDemoSubmodule.class));
    }

}
