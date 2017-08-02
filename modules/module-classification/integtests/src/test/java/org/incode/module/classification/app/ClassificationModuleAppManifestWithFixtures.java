package org.incode.module.classification.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.classification.fixture.ClassifiedDemoObjectsFixture;

/**
 * Run the app but without setting up any fixtures.
 */
public class ClassificationModuleAppManifestWithFixtures extends ClassificationModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(ClassifiedDemoObjectsFixture.class);
    }


}
