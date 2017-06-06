package org.isisaddons.module.togglz.webapp;

import org.isisaddons.module.togglz.fixture.dom.module.featuretoggle.TogglzDemoFeature;
import org.isisaddons.module.togglz.glue.spi.TogglzModuleFeatureManagerProviderAbstract;

/**
 * Registered in META-INF/services, as per http://www.togglz.org/documentation/advanced-config.html
 */
public class CustomTogglzModuleFeatureManagerProvider extends TogglzModuleFeatureManagerProviderAbstract {

    public CustomTogglzModuleFeatureManagerProvider() {
        super(TogglzDemoFeature.class);
    }

}