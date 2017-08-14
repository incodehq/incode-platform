package org.incode.platform.lib.stringinterpolator.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.stringinterpolator.fixture.StringInterpolatorDemoToDoItemsFixture;

public class StringInterpolatorLibAppManifestWithFixtures extends StringInterpolatorLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(StringInterpolatorDemoToDoItemsFixture.class);
    }


}
