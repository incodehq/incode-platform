package org.isisaddons.module.publishmq.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObject;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.dom.PublishMqDemoObjects;
import org.isisaddons.module.publishmq.fixture.demoapp.demomodule.fixturescripts.PublishMqDemoObject_create3;
import org.isisaddons.module.publishmq.integtests.PublishMqModuleIntegTestAbstract;

public class PublishMqDemoObjects_IntegTest extends PublishMqModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private PublishMqDemoObjects publishmqDemoObjects;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new PublishMqDemoObject_create3(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<PublishMqDemoObject> all = wrap(publishmqDemoObjects).listAllPublishMqDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        PublishMqDemoObject publishmqDemoObject = wrap(all.get(0));
        Assertions.assertThat(publishmqDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(publishmqDemoObjects).createPublishMqDemoObject("Faz");
        
        final List<PublishMqDemoObject> all = wrap(publishmqDemoObjects).listAllPublishMqDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}