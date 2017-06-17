package org.isisaddons.wicket.summernote.app;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SummernoteDemoAppManifestWithDemoFixture extends SummernoteDemoAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return Lists.<Class<? extends FixtureScript>>newArrayList(org.isisaddons.wicket.summernote.fixture.scripts.SummernoteEditorAppSetUpFixture.class); }

    @Override
    public Map<String, String> getConfigurationProperties() {
        Map<String,String> props = super.getConfigurationProperties();
        props.put("isis.persistor.datanucleus.install-fixtures","true");
        return props;
    }

}
