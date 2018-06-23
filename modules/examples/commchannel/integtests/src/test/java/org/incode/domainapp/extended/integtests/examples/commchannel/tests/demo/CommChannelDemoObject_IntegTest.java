package org.incode.domainapp.extended.integtests.examples.commchannel.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.incode.domainapp.extended.integtests.examples.commchannel.CommChannelModuleIntegTestAbstract;
import org.incode.example.alias.demo.shared.dom.DemoObject;
import org.incode.example.alias.demo.shared.dom.DemoObjectMenu;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        commChannelDemoObject = wrap(commChannelDemoObjectMenu).createDemoObject("Foo");
    }


}