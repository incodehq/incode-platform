package org.incode.module.docfragment.demo.application.manifest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.freemarker.dom.FreeMarkerModule;

import org.incode.module.docfragment.demo.application.fixture.DemoAppApplicationModuleFixtureSubmodule;
import org.incode.module.docfragment.demo.application.services.DemoAppApplicationModuleServicesSubmodule;
import org.incode.module.docfragment.demo.module.dom.DemoModuleDomSubmodule;
import org.incode.module.docfragment.dom.DocFragmentModuleDomModule;

/**
 * Bootstrap the application.
 */
public class DocFragmentAppAppManifest implements AppManifest {

    /**
     * Load all services and entities found in (the packages and subpackages within) these modules
     */
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                DocFragmentModuleDomModule.class,
                DemoModuleDomSubmodule.class,
                DemoAppApplicationModuleFixtureSubmodule.class,
                DemoAppApplicationModuleServicesSubmodule.class,

                FreeMarkerModule.class  // required by DocFragmentModule, do not yet support transitivity
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

}
