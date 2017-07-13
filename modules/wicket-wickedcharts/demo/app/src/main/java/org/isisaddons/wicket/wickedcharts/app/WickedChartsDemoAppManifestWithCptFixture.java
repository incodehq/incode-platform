package org.isisaddons.wicket.wickedcharts.app;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class WickedChartsDemoAppManifestWithCptFixture extends WickedChartsCptAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return Lists.<Class<? extends FixtureScript>>newArrayList(org.isisaddons.wicket.wickedcharts.fixture.scripts.WickedChartsWicketAppSetUpFixture.class); }

    @Override
    public Map<String, String> getConfigurationProperties() {
        Map<String,String> props = super.getConfigurationProperties();
        props.put("isis.persistor.datanucleus.install-fixtures","true");
        return props;
    }

}
