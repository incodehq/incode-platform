package org.incode.platform.dom.document.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.document.fixture.DocumentDemoAppDemoFixture;

/**
 * Run the app but without setting up any fixtures.
 */
public class DocumentModuleAppManifestWithFixtures extends DocumentModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DocumentDemoAppDemoFixture.class);
    }


}
