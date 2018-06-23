package org.incode.domainapp.extended.integtests.examples.commchannel.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.incode.domainapp.extended.integtests.examples.commchannel.CommChannelModuleIntegTestAbstract;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObject;
import org.incode.domainapp.extended.module.fixtures.shared.demo.dom.DemoObjectMenu;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    DemoObjectMenu commChannelDemoObjectMenu;

    DemoObject commChannelDemoObject;

    @Before
    public void setUpData() throws Exception {
        commChannelDemoObject = wrap(commChannelDemoObjectMenu).createDemoObject("Foo");
    }


}