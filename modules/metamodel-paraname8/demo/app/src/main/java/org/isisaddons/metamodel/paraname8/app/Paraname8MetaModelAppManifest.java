package org.isisaddons.metamodel.paraname8.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.metamodel.paraname8.fixture.Paraname8FixtureModule;

public class Paraname8MetaModelAppManifest implements AppManifest {

    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                Paraname8AppModule.class,
                Paraname8FixtureModule.class
        );
    }
    @Override
    public List<Class<?>> getAdditionalServices() {
        return Lists.newArrayList();
    }
    @Override
    public String getAuthenticationMechanism() { return null; }
    @Override
    public String getAuthorizationMechanism() { return null; }
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return null; }
    @Override
    public Map<String, String> getConfigurationProperties() { return null; }

}
