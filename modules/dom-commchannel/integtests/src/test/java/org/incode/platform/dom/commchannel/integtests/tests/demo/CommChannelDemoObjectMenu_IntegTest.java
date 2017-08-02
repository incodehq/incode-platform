package org.incode.platform.dom.commchannel.integtests.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.domainapp.example.dom.dom.commchannel.dom.demo.CommChannelDemoObject;
import org.incode.domainapp.example.dom.dom.commchannel.dom.demo.CommChannelDemoObjectMenu;
import org.incode.domainapp.example.dom.dom.commchannel.fixture.CommChannelDemoObjectsFixture;
import org.incode.platform.dom.commchannel.integtests.CommChannelModuleIntegTestAbstract;

public class CommChannelDemoObjectMenu_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private CommChannelDemoObjectMenu commChannelDemoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new CommChannelDemoObjectsFixture(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<CommChannelDemoObject> all = wrap(commChannelDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        CommChannelDemoObject commChannelDemoObject = wrap(all.get(0));
        Assertions.assertThat(commChannelDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(commChannelDemoObjectMenu).create("Faz");
        
        final List<CommChannelDemoObject> all = wrap(commChannelDemoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}