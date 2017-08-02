package org.isisaddons.module.poly.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.lib.poly.fixture.RecreateAll;

public class PolyLibAppManifestWithFixtures extends PolyLibAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(RecreateAll.class);
    }

}
