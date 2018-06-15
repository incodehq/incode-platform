package org.incode.platform.spi.audit.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.module.fixtures.per_cpt.spi.audit.fixture.SomeAuditedObject_and_SomeNonAuditedObject_recreate3;

public class AuditSpiAppManifestWithFixtures extends AuditSpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SomeAuditedObject_and_SomeNonAuditedObject_recreate3.class);
    }

}
