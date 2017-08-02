package org.incode.platform.dom.commchannel.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.exampledom.module.commchannel.fixture.CommChannelDemoObjectsFixture;

/**
 * Run the app but without setting up any fixtures.
 */
public class CommChannelModuleAppManifestWithFixtures extends CommChannelModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(CommChannelDemoObjectsFixture.class);
    }

}
