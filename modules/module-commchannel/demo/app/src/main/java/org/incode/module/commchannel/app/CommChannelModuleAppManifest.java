package org.incode.module.commchannel.app;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.wicket.gmap3.cpt.applib.Gmap3ApplibModule;
import org.isisaddons.wicket.gmap3.cpt.ui.Gmap3UiModule;

import org.incode.module.commchannel.dom.CommChannelModule;
import org.incode.module.commchannel.fixture.CommChannelFixtureModule;

/**
 * Bootstrap the application.
 */
public class CommChannelModuleAppManifest implements AppManifest {

    private final List<Class<?>> classes = Lists.newArrayList();

    public CommChannelModuleAppManifest() {
        withModules(
                CommChannelModule.class, // dom module
                CommChannelFixtureModule.class,
                CommChannelModuleAppModule.class,
                Gmap3ApplibModule.class,
                Gmap3UiModule.class
        );
    }

    public CommChannelModuleAppManifest withModules(Class<?>... classes) {
        for (Class<?> cls : classes) {
            this.classes.add(cls);
        }
        return this;
    }

    /**
     * Load all services and entities found in (the packages and subpackages within) these modules
     */
    @Override
    public List<Class<?>> getModules() {
        return Collections.unmodifiableList(classes);
    }

    /**
     * No additional services.
     */
    @Override
    public List<Class<?>> getAdditionalServices() {
        return Collections.emptyList();
    }

    /**
     * Use shiro for authentication.
     *
     * <p>
     *     NB: this is ignored for integration tests, which always use "bypass".
     * </p>
     */
    @Override
    public String getAuthenticationMechanism() {
        return "shiro";
    }

    /**
     * Use shiro for authorization.
     *
     * <p>
     *     NB: this is ignored for integration tests, which always use "bypass".
     * </p>
     */
    @Override
    public String getAuthorizationMechanism() {
        return "shiro";
    }

    /**
     * No fixtures.
     */
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Collections.emptyList();
    }

    /**
     * No overrides.
     */
    @Override
    public Map<String, String> getConfigurationProperties() {
        return null;
    }

}
