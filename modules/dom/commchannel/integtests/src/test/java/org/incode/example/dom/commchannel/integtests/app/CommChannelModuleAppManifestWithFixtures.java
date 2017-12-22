package org.incode.example.dom.commchannel.integtests.app;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.commchannel.fixture.DemoObject_withCommChannels_recreate3;

/**
 * Run the app but without setting up any fixtures.
 */
public class CommChannelModuleAppManifestWithFixtures extends CommChannelModuleAppManifest {

    @Override protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(DemoObject_withCommChannels_recreate3.class);
    }

}
