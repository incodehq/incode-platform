package org.isisaddons.module.publishmq.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.publishmq.PublishMqModule;
import org.isisaddons.module.publishmq.fixture.scripts.scenarios.PublishMqDemoObjectsFixture;

public class PublishMqAppManifest implements AppManifest {
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                PublishMqModule.class,
                PublishMqDemoObjectsFixture.class,
                PublishMqAppModule.class
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
