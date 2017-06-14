package org.isisaddons.module.sessionlogger.integtests;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;
import org.isisaddons.module.sessionlogger.app.SessionLoggerAppManifest;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with the app's domain services.
 */
public class SessionLoggerModuleSystemInitializer {
    
    private SessionLoggerModuleSystemInitializer(){}

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new IsisConfigurationForJdoIntegTests())
                    .with(new SessionLoggerAppManifest())
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }
}
