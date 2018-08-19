package org.isisaddons.module.togglz.integtests.tests;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.DemoObject;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.dom.DemoObjectMenu;
import org.isisaddons.module.togglz.fixture.demoapp.demomodule.fixturescripts.DemoObject_create3;
import org.isisaddons.module.togglz.integtests.TogglzModuleIntegTestAbstract;

public class TogglzDemoObjects_IntegTest extends TogglzModuleIntegTestAbstract {

    @Inject
    FixtureScripts fixtureScripts;

    @Inject
    private DemoObjectMenu demoObjectMenu;

    @Before
    public void setUpData() throws Exception {
        fixtureScripts.runFixtureScript(new DemoObject_create3(), null);
    }


    @Test
    public void listAll() throws Exception {

        final List<DemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(3);
        
        DemoObject togglzDemoObject = wrap(all.get(0));
        Assertions.assertThat(togglzDemoObject.getName()).isEqualTo("Foo");
    }
    
    @Test
    public void create() throws Exception {

        wrap(demoObjectMenu).createDemoObject("Faz");
        
        final List<DemoObject> all = wrap(demoObjectMenu).listAllDemoObjects();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }

}