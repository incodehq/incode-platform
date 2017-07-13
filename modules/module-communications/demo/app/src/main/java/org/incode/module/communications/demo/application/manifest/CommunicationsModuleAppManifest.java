package org.incode.module.communications.demo.application.manifest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.command.CommandModule;
import org.isisaddons.module.fakedata.FakeDataModule;
import org.isisaddons.module.freemarker.dom.FreeMarkerModule;
import org.isisaddons.module.pdfbox.dom.PdfBoxModule;

import org.incode.module.communications.demo.application.fixture.DemoAppApplicationModuleFixtureSubmodule;
import org.incode.module.communications.demo.application.services.DemoAppApplicationModuleServicesSubmodule;
import org.incode.module.communications.demo.module.dom.DemoModuleDomSubmodule;
import org.incode.module.communications.dom.CommunicationsModuleDomModule;
import org.incode.module.country.dom.CountryModule;
import org.incode.module.document.dom.DocumentModule;

/**
 * Bootstrap the application.
 */
public class CommunicationsModuleAppManifest implements AppManifest {

    /**
     * Load all services and entities found in (the packages and subpackages within) these modules
     */
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                CommunicationsModuleDomModule.class,
                DocumentModule.class,
                CountryModule.class,
                DemoModuleDomSubmodule.class,
                DemoAppApplicationModuleFixtureSubmodule.class,
                DemoAppApplicationModuleServicesSubmodule.class,
                PdfBoxModule.class,
                CommandModule.class,
                FreeMarkerModule.class,
                FakeDataModule.class
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
