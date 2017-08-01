package org.isisaddons.module.togglz.app;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.ext.togglz.fixture.TogglzDemoObjectsFixture;

public class TogglzDemoAppManifestWithExtFixture extends TogglzExtAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return Lists.<Class<? extends FixtureScript>>newArrayList(TogglzDemoObjectsFixture.class); }

    @Override
    public Map<String, String> getConfigurationProperties() {
        Map<String,String> props = super.getConfigurationProperties();
        props.put("isis.persistor.datanucleus.install-fixtures","true");
        return props;
    }

}
