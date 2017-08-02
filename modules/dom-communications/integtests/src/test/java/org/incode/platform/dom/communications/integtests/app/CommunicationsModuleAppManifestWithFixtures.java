package org.incode.platform.dom.communications.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.communications.fixture.DemoModuleFixture;

/**
 * Run the app but without setting up any fixtures.
 */
public class CommunicationsModuleAppManifestWithFixtures extends CommunicationsModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoModuleFixture.class);
    }

}
