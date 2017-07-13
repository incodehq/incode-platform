package org.isisaddons.module.togglz.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.settings.SettingsModule;
import org.isisaddons.module.togglz.TogglzModule;
import org.isisaddons.module.togglz.fixture.TogglzFixtureModule;

public class TogglzExtAppManifest implements AppManifest {
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                TogglzModule.class,
                TogglzFixtureModule.class,
                TogglzDemoAppModule.class,
                SecurityModule.class,
                SettingsModule.class
        );
    }
    @Override
    public List<Class<?>> getAdditionalServices() { return null; }
    @Override
    public String getAuthenticationMechanism() { return null; }
    @Override
    public String getAuthorizationMechanism() { return null; }
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return null; }
    @Override
    public Map<String, String> getConfigurationProperties() {
        HashMap<String,String> props = Maps.newHashMap();
        props.put("isis.viewer.wicket.rememberMe.cookieKey","DemoAppEncryptionKey");
        return props;
    }

}
