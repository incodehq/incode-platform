package org.isisaddons.metamodel.paraname8.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.metamodel.paraname8.fixture.Paraname8DemoObjectsFixture;

public class Paraname8MetaModelAppManifestWithFixtures extends Paraname8MetaModelAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(Paraname8DemoObjectsFixture.class);
    }

}
