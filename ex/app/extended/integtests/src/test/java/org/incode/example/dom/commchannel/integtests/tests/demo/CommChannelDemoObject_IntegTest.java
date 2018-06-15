package org.incode.example.dom.commchannel.integtests.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.domainapp.module.fixtures.per_cpt.examples.commchannel.fixture.DemoObject_withCommChannels_tearDown;
import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.module.fixtures.shared.demo.dom.DemoObjectMenu;
import org.incode.example.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_tearDown(), null);

        commChannelDemoObject = wrap(commChannelDemoObjectMenu).createDemoObject("Foo");
    }


}