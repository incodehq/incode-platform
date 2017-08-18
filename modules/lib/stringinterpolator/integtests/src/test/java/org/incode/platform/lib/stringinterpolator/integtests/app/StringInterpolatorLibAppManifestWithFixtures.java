package org.incode.platform.lib.stringinterpolator.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.demo.fixture.reminders.DemoReminder_recreate4;

public class StringInterpolatorLibAppManifestWithFixtures extends StringInterpolatorLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoReminder_recreate4.class);
    }


}
