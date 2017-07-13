package org.isisaddons.module.docx.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.docx.DocxModule;
import org.isisaddons.module.docx.fixture.DocxFixtureModule;

public class DocxModuleAppManifest implements AppManifest {

    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                DocxAppModule.class,
                DocxFixtureModule.class,
                DocxModule.class
        );
    }
    @Override
    public List<Class<?>> getAdditionalServices() {
        return Lists.newArrayList();
    }
    @Override
    public String getAuthenticationMechanism() { return null; }
    @Override
    public String getAuthorizationMechanism() { return null; }
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return null; }
    @Override
    public Map<String, String> getConfigurationProperties() { return null; }

}
