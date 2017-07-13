package org.isisaddons.module.settings.integtests;

import org.apache.log4j.Level;

import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import org.isisaddons.module.settings.app.SettingsModuleAppManifest;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with the app's domain services. 
 */
public class SettingsModuleSystemInitializer {
    
    private SettingsModuleSystemInitializer(){}

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = new IsisSystemForTest.Builder()
                    .withLoggingAt(Level.INFO)
                    .with(new SettingsModuleAppManifest())
                    .with(new IsisConfigurationForJdoIntegTests())
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }


}
