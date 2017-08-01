package org.isisaddons.metamodel.paraname8.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.metamodel.paraname8.fixture.Paraname8DemoObjectsFixture;

public class Paraname8MetaModelAppManifestWithFixtures extends Paraname8MetaModelAppManifest {

    /**
     * Fixtures to be installed.
     */
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Lists.<Class<? extends FixtureScript>>newArrayList(
                Paraname8DemoObjectsFixture.class);
    }

    /**
     * Force fixtures to be loaded.
     */
    @Override
    public Map<String, String> getConfigurationProperties() {
        HashMap<String,String> props = Maps.newHashMap();
        props.put("isis.persistor.datanucleus.install-fixtures","true");
        return props;
    }

}
