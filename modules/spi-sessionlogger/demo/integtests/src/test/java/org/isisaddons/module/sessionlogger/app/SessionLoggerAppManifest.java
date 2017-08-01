package org.isisaddons.module.sessionlogger.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.spi.sessionlogger.ExampleDomSpiSessionLoggerModule;
import org.isisaddons.module.sessionlogger.SessionLoggerModule;

public final class SessionLoggerAppManifest implements AppManifest {
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                SessionLoggerModule.class,
                ExampleDomSpiSessionLoggerModule.class,
                SessionLoggerAppModule.class
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
    public Map<String, String> getConfigurationProperties() { return null; }

}
