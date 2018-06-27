package org.incode.example.commchannel.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomer;
import org.incode.example.commchannel.demo.shared.dom.CommChannelCustomerMenu;
import org.incode.example.commchannel.demo.usage.fixture.CommChannelCustomer_withCommChannels_create3;
import org.incode.example.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommChannelDemoObjectMenu_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private CommChannelCustomerMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelCustomer_withCommChannels_create3(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<CommChannelCustomer> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        CommChannelCustomer commChannelCommChannelCustomer = wrap(all.get(0));
        Assertions.assertThat(commChannelCommChannelCustomer.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).createDemoObject("Faz");
        
        final List<CommChannelCustomer> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}