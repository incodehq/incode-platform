package org.incode.platform.dom.commchannel.integtests.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.CommChannelDemoObjectsTearDownFixture;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsTearDownFixture(), null);

        commChannelDemoObject = wrap(commChannelDemoObjectMenu).create("Foo");
    }


}