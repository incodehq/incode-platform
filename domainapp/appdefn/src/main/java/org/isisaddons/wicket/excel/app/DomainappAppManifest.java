package org.isisaddons.wicket.excel.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.metamodel.paraname8.app.Paraname8AppModule;
import org.isisaddons.metamodel.paraname8.fixture.Paraname8FixtureModule;
import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.fixture.SecurityFixtureModule;
import org.isisaddons.wicket.excel.cpt.ui.ExcelUiModule;
import org.isisaddons.wicket.excel.fixture.ExcelCptDemoFixtureModule;

public class DomainappAppManifest implements AppManifest {

    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(

                SecurityModule.class,

                Paraname8AppModule.class,
                Paraname8FixtureModule.class,

                ExcelUiModule.class,
                ExcelCptDemoFixtureModule.class,
                ExcelCptDemoAppModule.class
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
        props.put("isis.viewer.wicket.rememberMe.cookieKey","DomainAppEncryptionKey");
        return props;
    }

}
