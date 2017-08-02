package org.incode.platform.spi.audit.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.spi.audit.fixture.AuditDemoAppFixture;

public class AuditSpiAppManifestWithFixtures extends AuditSpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(AuditDemoAppFixture.class);
    }

}
