package org.incode.domainapp.extended.integtests.examples.commchannel.tests.demo;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.domainapp.extended.integtests.examples.commchannel.CommChannelModuleIntegTestAbstract;
import org.incode.example.alias.demo.examples.commchannel.fixture.DemoObject_withCommChannels_create3;
import org.incode.example.alias.demo.shared.dom.DemoObject;
import org.incode.example.alias.demo.shared.dom.DemoObjectMenu;

public class CommChannelDemoObjectMenu_IntegTest extends CommChannelModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private DemoObjectMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_withCommChannels_create3(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        DemoObject commChannelDemoObject = wrap(all.get(0));
        Assertions.assertThat(commChannelDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).createDemoObject("Faz");
        
        final List<DemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}