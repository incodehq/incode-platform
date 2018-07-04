package org.incode.example.commchannel.integtests.tests.demo;

import javax.inject.Inject;

import org.junit.Before;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomerMenu;
import org.incode.example.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommChannelDemoObject_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    CommChannelCustomerMenu commChannelDemoObjectMenu;

    CommChannelCustomer commChannelCommChannelCustomer;

    @Before
    public void setUpData() throws Exception {
        commChannelCommChannelCustomer = wrap(commChannelDemoObjectMenu).createDemoObject("Foo");
    }


}