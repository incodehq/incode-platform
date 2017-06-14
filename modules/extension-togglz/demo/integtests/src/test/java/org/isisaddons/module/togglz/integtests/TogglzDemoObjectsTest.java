package org.isisaddons.module.togglz.integtests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.togglz.fixture.dom.module.demo.TogglzDemoObject;
import org.isisaddons.module.togglz.fixture.dom.module.demo.TogglzDemoObjects;
import org.isisaddons.module.togglz.fixture.scripts.scenarios.TogglzDemoObjectsFixture;


public class TogglzDemoObjectsTest extends TogglzModuleIntegTest {

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