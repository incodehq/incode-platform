package org.isisaddons.module.audit.integtests;

import org.apache.log4j.Level;

import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import org.isisaddons.module.audit.app.AuditSpiAppManifest;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with the app's domain services.
 */
public class AuditModuleSystemInitializer {
    
    private AuditModuleSystemInitializer(){}

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(Level.INFO)
                    .with(new AuditSpiAppManifest())
                    .with(new IsisConfigurationForJdoIntegTests())
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }

}
