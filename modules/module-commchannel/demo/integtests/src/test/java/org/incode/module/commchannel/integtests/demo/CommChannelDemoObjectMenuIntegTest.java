package org.incode.module.commchannel.integtests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import domainapp.modules.exampledom.module.commchannel.dom.demo.CommChannelDemoObject;
import domainapp.modules.exampledom.module.commchannel.dom.demo.CommChannelDemoObjectMenu;
import domainapp.modules.exampledom.module.commchannel.fixture.CommChannelDemoObjectsFixture;
import org.incode.module.commchannel.integtests.CommChannelModuleIntegTest;

public class CommChannelDemoObjectMenuIntegTest extends CommChannelModuleIntegTest {

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