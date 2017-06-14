package org.incode.module.commchannel.integtests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.module.commchannel.fixture.dom.CommChannelDemoObject;
import org.incode.module.commchannel.fixture.dom.CommChannelDemoObjectMenu;
import org.incode.module.commchannel.fixture.scripts.teardown.CommChannelDemoObjectsTearDownFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

public class CommChannelDemoObjectIntegTest extends CommChannelModuleIntegTest {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    CommChannelDemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        commChannelDemoObject = wrap(commChannelDemoObjectMenu).create("Foo");
    }


}