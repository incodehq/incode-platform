package org.incode.platform.lib.poly.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.fixture.Case_FixedAsset_Party_recreateAll;

public class PolyLibAppManifestWithFixtures extends PolyLibAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(Case_FixedAsset_Party_recreateAll.class);
    }

}
