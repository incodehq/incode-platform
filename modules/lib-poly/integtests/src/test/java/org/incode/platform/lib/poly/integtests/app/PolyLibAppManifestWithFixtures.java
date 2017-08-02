package org.incode.platform.lib.poly.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.poly.fixture.RecreateAll;

public class PolyLibAppManifestWithFixtures extends PolyLibAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(RecreateAll.class);
    }

}
