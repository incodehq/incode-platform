package org.isisaddons.module.tags.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.tags.TagsModule;
import org.isisaddons.module.tags.fixture.TagsFixtureModule;

public class TagsModuleAppManifest implements AppManifest {
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                TagsModule.class,
                TagsFixtureModule.class,
                org.isisaddons.module.tags.fixture.TagsFixtureModule.class
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
