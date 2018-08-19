package org.isisaddons.module.togglz.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.togglz.fixture.demoapp.demomodule.fixturescripts.DemoObject_create3;

public class TogglzDemoAppManifestWithExtFixture extends TogglzExtAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObject_create3.class); }


}
