package org.isisaddons.wicket.gmap3.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.wicket.gmap3.fixture.scripts.Gmap3DemoSetUpFixture;

/**
 * Run the app but without setting up any fixtures.
 */
public class Gmap3CptAppManifestWithFixtures extends Gmap3CptAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return (List) Lists.newArrayList(Gmap3DemoSetUpFixture.class);
    }

    @Override
    public Map<String, String> getConfigurationProperties() {
        HashMap<String,String> props = Maps.newHashMap();
        props.put("isis.persistor.datanucleus.install-fixtures","true");
        return props;
    }

}
