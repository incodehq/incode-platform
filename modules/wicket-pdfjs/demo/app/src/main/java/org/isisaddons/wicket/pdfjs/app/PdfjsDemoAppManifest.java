package org.isisaddons.wicket.pdfjs.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.wicket.pdfjs.cpt.PdfjsCptModule;
import org.isisaddons.wicket.pdfjs.fixture.PdfjsFixtureModule;

public class PdfjsDemoAppManifest implements AppManifest {
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                PdfjsCptModule.class,
                PdfjsFixtureModule.class,
                PdfjsAppModule.class,

                FakeDataModule.class
        );
    }
    @Override
    public List<Class<?>> getAdditionalServices() { return null; }
    @Override
    public String getAuthenticationMechanism() { return null; }
    @Override
    public String getAuthorizationMechanism() { return null; }
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() { return null; }
    @Override
    public Map<String, String> getConfigurationProperties() {
        HashMap<String,String> props = Maps.newHashMap();
        props.put("isis.viewer.wicket.rememberMe.cookieKey","DemoAppEncryptionKey");
        return props;
    }

}
