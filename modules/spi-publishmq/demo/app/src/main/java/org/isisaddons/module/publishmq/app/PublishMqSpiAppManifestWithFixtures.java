package org.isisaddons.module.publishmq.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.spi.publishmq.fixture.PublishMqDemoObjectsFixture;

public class PublishMqSpiAppManifestWithFixtures extends PublishMqSpiAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Arrays.<Class<? extends FixtureScript>>asList(
                PublishMqDemoObjectsFixture.class);

    }
    @Override
    public Map<String, String> getConfigurationProperties() {
        final Map<String, String> props = Maps.newHashMap();
        props.put("isis.persistor.datanucleus.install-fixtures", "true");
        return props;
    }

}
