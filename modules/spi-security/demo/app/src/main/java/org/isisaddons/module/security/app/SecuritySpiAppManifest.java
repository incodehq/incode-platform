package org.isisaddons.module.security.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.SecurityModule;
import org.isisaddons.module.security.fixture.SecurityFixtureModule;

public class SecuritySpiAppManifest implements AppManifest {

    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                SecurityModule.class,
                SecurityFixtureModule.class,
                SecurityAppModule.class
        );
    }
    @Override
    public List<Class<?>> getAdditionalServices() {
        return Lists.newArrayList(org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt.class
            ,org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceAllowBeatsVeto.class
            //,org.isisaddons.module.security.dom.permission.PermissionsEvaluationServiceVetoBeatsAllow.class
        );
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
