package org.incode.platformapp.appdefn.togglz;

import org.isisaddons.module.togglz.fixture.feature.TogglzFeature;
import org.isisaddons.module.togglz.glue.spi.TogglzModuleFeatureManagerProviderAbstract;

/**
 * Registered in META-INF/services, as per http://www.togglz.org/documentation/advanced-config.html
 */
public class CustomTogglzModuleFeatureManagerProvider extends TogglzModuleFeatureManagerProviderAbstract {

    public CustomTogglzModuleFeatureManagerProvider() {
        super(TogglzFeature.class);
    }

}