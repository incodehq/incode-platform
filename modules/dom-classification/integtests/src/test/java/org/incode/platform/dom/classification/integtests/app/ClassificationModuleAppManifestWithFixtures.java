package org.incode.platform.dom.classification.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.classification.fixture.ClassifiedDemoObjectsFixture;

/**
 * Run the app but without setting up any fixtures.
 */
public class ClassificationModuleAppManifestWithFixtures extends ClassificationModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(ClassifiedDemoObjectsFixture.class);
    }


}
