package org.isisaddons.module.audit.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.audit.fixture.scripts.AuditDemoAppFixture;

public class AuditAppManifestWithFixtures extends AuditAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Arrays.<Class<? extends FixtureScript>>asList(
                AuditDemoAppFixture.class);

    }
    @Override
    public Map<String, String> getConfigurationProperties() {
        final Map<String, String> props = Maps.newHashMap();
        props.put("isis.persistor.datanucleus.install-fixtures", "true");
        return props;
    }

}
