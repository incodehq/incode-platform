package org.incode.domainapp.extended.integtests.lib.fakedata.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.fakedata.fixture.DemoObjectWithAll_recreate3;

public class FakedataLibAppManifestWithFixtures extends FakedataLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObjectWithAll_recreate3.class);
    }


}
