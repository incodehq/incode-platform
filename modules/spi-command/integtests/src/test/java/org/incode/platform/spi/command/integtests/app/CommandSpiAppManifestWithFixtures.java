package org.incode.platform.spi.command.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.spi.command.fixture.SomeCommandAnnotatedObjectsFixture;

public final class CommandSpiAppManifestWithFixtures extends CommandSpiAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SomeCommandAnnotatedObjectsFixture.class);
    }

}
