package domainapp.application.manifest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.application.fixture.FlywayDemoFixtureSubmodule;
import domainapp.application.services.FlywayDemoServicesSubmodule;
import domainapp.modules.exampledom.ext.flywaydb.ExampleDomExtFlywayDbModule;

/**
 * Bootstrap the application.
 */
public class FlywayDbDemoAppManifest implements AppManifest {

    /**
     * Load all services and entities found in (the packages and subpackages within) these modules
     */
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                ExampleDomExtFlywayDbModule.class,
                FlywayDemoFixtureSubmodule.class,
                FlywayDemoServicesSubmodule.class
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
