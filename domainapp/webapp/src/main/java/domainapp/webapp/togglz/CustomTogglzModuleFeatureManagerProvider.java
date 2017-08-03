package domainapp.webapp.togglz;

import org.isisaddons.module.togglz.glue.spi.TogglzModuleFeatureManagerProviderAbstract;

import org.incode.domainapp.example.dom.ext.togglz.dom.featuretoggle.TogglzDemoFeature;

/**
 * Registered in META-INF/services, as per http://www.togglz.org/documentation/advanced-config.html
 */
public class CustomTogglzModuleFeatureManagerProvider extends TogglzModuleFeatureManagerProviderAbstract {

    public CustomTogglzModuleFeatureManagerProvider() {
        super(TogglzDemoFeature.class);
    }

}