package org.incode.platform.dom.commchannel.integtests.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.domainapp.example.dom.dom.commchannel.dom.demo.CommChannelDemoObject;
import org.incode.domainapp.example.dom.dom.commchannel.dom.demo.CommChannelDemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.CommChannelDemoObjectsTearDownFixture;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

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