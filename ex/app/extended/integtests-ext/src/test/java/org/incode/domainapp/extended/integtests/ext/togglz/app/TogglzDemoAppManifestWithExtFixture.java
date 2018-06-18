package org.incode.domainapp.extended.integtests.ext.togglz.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.ext.togglz.fixture.DemoObject_create3;

public class TogglzDemoAppManifestWithExtFixture extends TogglzExtAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObject_create3.class); }


}
