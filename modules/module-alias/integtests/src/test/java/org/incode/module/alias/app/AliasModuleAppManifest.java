package org.incode.module.alias.app;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.module.alias.dom.AliasModule;
import domainapp.modules.exampledom.module.alias.ExampleDomModuleAliasModule;

/**
 * Bootstrap the application.
 */
public class AliasModuleAppManifest implements AppManifest {

    private final List<Class<?>> classes = Lists.newArrayList();

    public AliasModuleAppManifest() {
        withModules(
                AliasModule.class, // dom module
                ExampleDomModuleAliasModule.class,
                AliasAppModule.class
        );
    }

    public AliasModuleAppManifest withModules(Class<?>... classes) {
        for (Class<?> cls : classes) {
            this.classes.add(cls);
        }
        return this;
    }

    /**
     * Load all services and entities found in (the packages and subpackages within) these modules
     */
    @Override
    public List<Class<?>> getModules() {
        return Collections.unmodifiableList(classes);
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
     *
     * <p>
     *     NB: this is ignored for integration tests, which always use "bypass".
     * </p>
     */
    @Override
    public String getAuthenticationMechanism() {
        return "shiro";
    }

    /**
     * Use shiro for authorization.
     *
     * <p>
     *     NB: this is ignored for integration tests, which always use "bypass".
     * </p>
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
