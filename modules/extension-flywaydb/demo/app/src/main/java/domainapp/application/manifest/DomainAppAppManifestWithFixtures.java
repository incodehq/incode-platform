package domainapp.application.manifest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.fixture.scenario.RecreateFlywayDemoObjects;

/**
 * Run the app but without setting up any fixtures.
 */
public class DomainAppAppManifestWithFixtures extends DomainAppAppManifest {

    /**
     * Fixtures to be installed.
     */
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Lists.newArrayList(RecreateFlywayDemoObjects.class);
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
