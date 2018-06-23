package org.incode.examples.commchannel.integtests.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.incode.examples.commchannel.integtests.CommChannelModuleIntegTestAbstract;
import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObject;
import org.incode.examples.commchannel.demo.shared.demo.dom.DemoObjectMenu;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        commChannelDemoObject = wrap(commChannelDemoObjectMenu).createDemoObject("Foo");
    }


}