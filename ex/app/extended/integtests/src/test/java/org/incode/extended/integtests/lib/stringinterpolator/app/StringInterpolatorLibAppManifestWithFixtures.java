package org.incode.extended.integtests.lib.stringinterpolator.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.reminder.fixture.DemoReminder_recreate4;

public class StringInterpolatorLibAppManifestWithFixtures extends StringInterpolatorLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoReminder_recreate4.class);
    }


}
