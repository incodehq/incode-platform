package org.isisaddons.module.fakedata.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.lib.fakedata.fixture.FakeDataDemoObjectsScenario;

public class FakedataLibAppManifestWithFixtures extends FakedataLibAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(FakeDataDemoObjectsScenario.class);
    }


}
