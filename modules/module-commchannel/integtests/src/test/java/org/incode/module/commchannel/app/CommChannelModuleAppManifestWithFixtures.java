package org.incode.module.commchannel.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
