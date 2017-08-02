package org.incode.platform.dom.docfragment.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.docfragment.fixture.DemoAppFixture;

/**
 * Run the app but without setting up any fixtures.
 */
public class DocFragmentAppAppManifestWithFixtures extends DocFragmentAppAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoAppFixture.class);
    }
}
