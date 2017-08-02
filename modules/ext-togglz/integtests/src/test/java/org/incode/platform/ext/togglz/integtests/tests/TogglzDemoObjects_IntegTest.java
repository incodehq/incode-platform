package org.incode.platform.ext.togglz.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.incode.platform.ext.togglz.integtests.TogglzModuleIntegTestAbstract;

import org.incode.domainapp.example.dom.ext.togglz.dom.demo.TogglzDemoObject;
import org.incode.domainapp.example.dom.ext.togglz.dom.demo.TogglzDemoObjects;
import org.incode.domainapp.example.dom.ext.togglz.fixture.TogglzDemoObjectsFixture;


public class TogglzDemoObjects_IntegTest extends TogglzModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private TogglzDemoObjects togglzDemoObjects;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new TogglzDemoObjectsFixture(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<TogglzDemoObject> all = wrap(togglzDemoObjects).listAll();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        TogglzDemoObject togglzDemoObject = wrap(all.get(0));
        Assertions.assertThat(togglzDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(togglzDemoObjects).create("Faz");
        
        final List<TogglzDemoObject> all = wrap(togglzDemoObjects).listAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}