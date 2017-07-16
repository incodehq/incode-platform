package org.isisaddons.wicket.fullcalendar2.app;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.wicket.fullcalendar2.fixture.FullCalendar2WicketSetUpFixture;

public class FullCalendar2DemoAppManifestWithCptFixture extends FullCalendar2CptAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return Lists.<Class<? extends FixtureScript>>newArrayList(
            FullCalendar2WicketSetUpFixture.class); }

    @Override
    public Map<String, String> getConfigurationProperties() {
        Map<String,String> props = super.getConfigurationProperties();
        props.put("isis.persistor.datanucleus.install-fixtures","true");
        return props;
    }

}
