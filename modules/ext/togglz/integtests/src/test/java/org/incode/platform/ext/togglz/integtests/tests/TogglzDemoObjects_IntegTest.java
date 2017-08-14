package org.incode.platform.ext.togglz.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.domainapp.example.dom.demo.dom.demo.DemoObject;
import org.incode.domainapp.example.dom.demo.dom.demo.DemoObjectMenu;
import org.incode.domainapp.example.dom.ext.togglz.fixture.DemoObjectsFixture;
import org.incode.platform.ext.togglz.integtests.TogglzModuleIntegTestAbstract;


public class TogglzDemoObjects_IntegTest extends TogglzModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private DemoObjectMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObjectsFixture(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        DemoObject togglzDemoObject = wrap(all.get(0));
        Assertions.assertThat(togglzDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).create("Faz");
        
        final List<DemoObject> all = wrap(demoObjectMenu).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}