package org.incode.platform.lib.fakedata.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.fakedata.fixture.FakeDataDemoObjectsScenario;

public class FakedataLibAppManifestWithFixtures extends FakedataLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(FakeDataDemoObjectsScenario.class);
    }


}
