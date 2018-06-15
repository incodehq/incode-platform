package org.incode.platform.spi.security.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.module.fixtures.per_cpt.spi.security.fixture.SecurityModuleAppSetUp;

public class SecuritySpiAppManifestWithFixtures extends SecuritySpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SecurityModuleAppSetUp.class);
    }
}
