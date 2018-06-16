package org.incode.domainapp.extended.integtests.spi.sessionlogger;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.incode.domainapp.extended.module.fixtures.shared.FixturesModuleSharedSubmodule;
import org.incode.domainapp.extended.integtests.spi.sessionlogger.app.SessionLoggerAppManifest;

public abstract class SessionLoggerModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(SessionLoggerAppManifest.BUILDER
                .withAdditionalModules(FixturesModuleSharedSubmodule.class));
    }

}
