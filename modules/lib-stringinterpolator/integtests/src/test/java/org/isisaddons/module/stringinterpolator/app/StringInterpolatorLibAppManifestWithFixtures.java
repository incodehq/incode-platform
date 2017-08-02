package org.isisaddons.module.stringinterpolator.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.lib.stringinterpolator.fixture.StringInterpolatorDemoToDoItemsFixture;

public class StringInterpolatorLibAppManifestWithFixtures extends StringInterpolatorLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(StringInterpolatorDemoToDoItemsFixture.class);
    }


}
