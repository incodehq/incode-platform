package org.incode.extended.integtests.spi.audit.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.audit.fixture.SomeAuditedObject_and_SomeNonAuditedObject_recreate3;

public class AuditSpiAppManifestWithFixtures extends AuditSpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SomeAuditedObject_and_SomeNonAuditedObject_recreate3.class);
    }

}
