package org.incode.platform.demo.webapp.app.manifest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.application.fixture.DomainAppApplicationModuleFixtureSubmodule;
import domainapp.application.services.DomainAppApplicationModuleServicesSubmodule;
import domainapp.modules.simple.dom.SimpleModuleDomSubmodule;

/**
 * Bootstrap the application.
 */
public class DomainappAppManifest implements AppManifest {

    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                SimpleModuleDomSubmodule.class,
                DomainAppApplicationModuleFixtureSubmodule.class,
                DomainAppApplicationModuleServicesSubmodule.class
        );
    }

    /**
     * No additional services.
     */
    @Override
    public List<Class<?>> getAdditionalServices() {
        return Collections.emptyList();
    }

    /**
     * Use shiro for authentication.
     */
    @Override
    public String getAuthenticationMechanism() {
        return "shiro";
    }

    /**
     * Use shiro for authorization.
     */
    @Override
    public String getAuthorizationMechanism() {
        return "shiro";
    }

    /**
     * No fixtures.
     */
    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Collections.emptyList();
    }

    /**
     * No overrides.
     */
    @Override
    public Map<String, String> getConfigurationProperties() {
        return null;
    }


//    /**
//     * Fixtures to be installed.
//     */
//    @Override
//    public List<Class<? extends FixtureScript>> getFixtures() {
//        return Lists.newArrayList(RecreateFlywayDemoObjects.class);
//    }
//
//    /**
//     * Force fixtures to be loaded.
//     */
//    @Override
//    public Map<String, String> getConfigurationProperties() {
//        HashMap<String,String> props = Maps.newHashMap();
//        props.put("isis.persistor.datanucleus.install-fixtures","true");
//        return props;
//    }

}
