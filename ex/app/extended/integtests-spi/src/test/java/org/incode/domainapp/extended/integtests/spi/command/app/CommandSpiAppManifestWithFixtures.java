package org.incode.domainapp.extended.integtests.spi.command.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.spi.command.fixture.SomeCommandAnnotatedObject_recreate3;

public final class CommandSpiAppManifestWithFixtures extends CommandSpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SomeCommandAnnotatedObject_recreate3.class);
    }

}
