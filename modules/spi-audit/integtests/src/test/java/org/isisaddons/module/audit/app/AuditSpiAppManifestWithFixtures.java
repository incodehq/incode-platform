package org.isisaddons.module.audit.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.spi.audit.fixture.AuditDemoAppFixture;

public class AuditSpiAppManifestWithFixtures extends AuditSpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(AuditDemoAppFixture.class);
    }

}
