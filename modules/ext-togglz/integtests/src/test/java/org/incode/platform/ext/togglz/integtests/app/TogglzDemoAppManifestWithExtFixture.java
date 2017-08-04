package org.incode.platform.ext.togglz.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.ext.togglz.fixture.DemoObjectsFixture;

public class TogglzDemoAppManifestWithExtFixture extends TogglzExtAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObjectsFixture.class); }


}
