package org.isisaddons.module.sessionlogger.integtests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract2;

import org.isisaddons.module.sessionlogger.app.SessionLoggerAppManifest;

public abstract class SessionLoggerModuleIntegTestAbstract extends IntegrationTestAbstract2 {

    @BeforeClass
    public static void initClass() {
        bootstrapUsing(new SessionLoggerAppManifest());
    }

}
