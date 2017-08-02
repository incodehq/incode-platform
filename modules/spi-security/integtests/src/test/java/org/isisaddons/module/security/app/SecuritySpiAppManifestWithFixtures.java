package org.isisaddons.module.security.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.spi.security.fixture.SecurityModuleAppSetUp;

public class SecuritySpiAppManifestWithFixtures extends SecuritySpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SecurityModuleAppSetUp.class);
    }
}
