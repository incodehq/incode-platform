package org.incode.example.commchannel.integtests.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.incode.example.commchannel.demo.shared.dom.DemoObject;
import org.incode.example.commchannel.demo.shared.dom.DemoObjectMenu;
import org.incode.example.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        commChannelDemoObject = wrap(commChannelDemoObjectMenu).createDemoObject("Foo");
    }


}