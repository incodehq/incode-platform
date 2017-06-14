package org.incode.module.communications.dom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.SecurityModule;

/**
 * Provided for <tt>isis-maven-plugin</tt>.
 */
public class CommunicationsModuleDomManifest implements AppManifest {

    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                CommunicationsModule.class,  // domain (entities and repositories)
                SecurityModule.class
        );
    }

    @Override
    public List<Class<?>> getAdditionalServices() {
        return Collections.emptyList();
    }

    @Override
    public String getAuthenticationMechanism() {
        return null;
    }

    @Override
    public String getAuthorizationMechanism() {
        return null;
    }

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return null;
    }

    @Override
    public Map<String, String> getConfigurationProperties() {
        final Map<String, String> map = Maps.newHashMap();
        // required because includes the security module,
        // with the seed service that requires its database tables
        Util.withJavaxJdoRunInMemoryProperties(map);
        Util.withDataNucleusProperties(map);
        Util.withIsisIntegTestProperties(map);
        return map;
    }

}
