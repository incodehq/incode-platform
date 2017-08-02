package org.incode.platform.ext.togglz.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.ext.togglz.fixture.TogglzDemoObjectsFixture;

public class TogglzDemoAppManifestWithExtFixture extends TogglzExtAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(TogglzDemoObjectsFixture.class); }


}
