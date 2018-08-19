package org.isisaddons.module.togglz.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.TogglzDemoObject;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.TogglzDemoObjectMenu;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.fixturescripts.TogglzDemoObject_create3;
import org.isisaddons.module.togglz.integtests.TogglzModuleIntegTestAbstract;

public class TogglzDemoObjects_IntegTest extends TogglzModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private TogglzDemoObjectMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new TogglzDemoObject_create3(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<TogglzDemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        TogglzDemoObject togglzDemoObject = wrap(all.get(0));
        Assertions.assertThat(togglzDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).createDemoObject("Faz");
        
        final List<TogglzDemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}